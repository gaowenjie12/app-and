
package com.zsoe.businesssharing.commonview.recyclerview;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.zsoe.businesssharing.commonview.recyclerview.animation.AlphaInAnimation;
import com.zsoe.businesssharing.commonview.recyclerview.animation.BaseAnimation;
import com.zsoe.businesssharing.commonview.recyclerview.animation.ScaleInAnimation;
import com.zsoe.businesssharing.commonview.recyclerview.animation.SlideInBottomAnimation;
import com.zsoe.businesssharing.commonview.recyclerview.animation.SlideInLeftAnimation;
import com.zsoe.businesssharing.commonview.recyclerview.animation.SlideInRightAnimation;
import com.zsoe.businesssharing.commonview.recyclerview.entity.IExpandable;
import com.zsoe.businesssharing.commonview.recyclerview.loadmore.LoadMoreContainer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public abstract class BaseQuickAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean mIsFooter = true;
    private boolean mFirstOnlyEnable = true;
    private boolean mOpenAnimationEnable = false;
    private boolean mEmptyEnable;
    private boolean mHeadAndEmptyEnable;
    private boolean mFootAndEmptyEnable;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mDuration = 300;
    private int mLastPosition = -1;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener;
    @AnimationType
    private BaseAnimation mCustomAnimation;
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();
    private LinearLayout mHeaderLayout;
    private LinearLayout mCopyHeaderLayout = null;
    private View mFooterView;
    //    private int size = -1;
    private View mContentView;
    /**
     * View to show if there are no items to show.
     */
    private View mEmptyView;

    protected static final String TAG = BaseQuickAdapter.class.getSimpleName();
    protected Context mContext;
    protected int mLayoutResId;
    protected LayoutInflater mLayoutInflater;
    protected List<T> mData;
    public static final int HEADER_VIEW = 0x00000111;
    public static final int LOADING_VIEW = 0x00000222;
    public static final int FOOTER_VIEW = 0x00000333;
    public static final int EMPTY_VIEW = 0x00000555;

    private LoadMoreContainer loadMoreContainer;

    @IntDef({ALPHAIN, SCALEIN, SLIDEIN_BOTTOM, SLIDEIN_LEFT, SLIDEIN_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int ALPHAIN = 0x00000001;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SCALEIN = 0x00000002;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_BOTTOM = 0x00000003;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_LEFT = 0x00000004;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_RIGHT = 0x00000005;


    public void setLoadMoreContainer(LoadMoreContainer loadMoreContainer) {
        this.loadMoreContainer = loadMoreContainer;
    }

    /**
     * Sets the duration of the animation.
     *
     * @param duration The length of the animation, in milliseconds.
     */
    public void setDuration(int duration) {
        mDuration = duration;
    }


    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked.
     *
     * @param onRecyclerViewItemClickListener The callback that will be invoked.
     */
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * AdapterView has been clicked.
     */
    public interface OnRecyclerViewItemClickListener {
        /**
         * Callback method to be invoked when an item in this AdapterView has
         * been clicked.
         *
         * @param view     The view within the AdapterView that was clicked (this
         *                 will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         */
        public void onItemClick(View view, int position);
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked and held
     *
     * @param onRecyclerViewItemLongClickListener The callback that will run
     */
    public void setOnRecyclerViewItemLongClickListener(OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener) {
        this.onRecyclerViewItemLongClickListener = onRecyclerViewItemLongClickListener;
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * view has been clicked and held
     */
    public interface OnRecyclerViewItemLongClickListener {
        /**
         * callback method to be invoked when an item in this view has been
         * click and held
         *
         * @param view     The view whihin the AbsListView that was clicked
         * @param position The position of the view int the adapter
         * @return true if the callback consumed the long click ,false otherwise
         */
        public boolean onItemLongClick(View view, int position);
    }

    private OnRecyclerViewItemChildClickListener mChildClickListener;

    /**
     * Register a callback to be invoked when childView in this AdapterView has
     * been clicked and held
     * {@link OnRecyclerViewItemChildClickListener}
     *
     * @param childClickListener The callback that will run
     */
    public void setOnRecyclerViewItemChildClickListener(OnRecyclerViewItemChildClickListener childClickListener) {
        this.mChildClickListener = childClickListener;
    }

    public interface OnRecyclerViewItemChildClickListener {
        void onItemChildClick(BaseQuickAdapter adapter, View view, int position);
    }

    public class OnItemChildClickListener implements View.OnClickListener {
        public RecyclerView.ViewHolder mViewHolder;

        @Override
        public void onClick(View v) {
            if (mChildClickListener != null)
                mChildClickListener.onItemChildClick(BaseQuickAdapter.this, v, mViewHolder.getLayoutPosition() - getHeaderViewsCount());
        }
    }


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public BaseQuickAdapter(int layoutResId, List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }

    public BaseQuickAdapter(List<T> data) {
        this(0, data);
    }

    public BaseQuickAdapter(View contentView, List<T> data) {
        this(0, data);
        mContentView = contentView;
    }

    /**
     * remove the item associated with the specified position of adapter
     *
     * @param position
     */
    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position + getHeaderViewsCount());

    }

    /**
     * insert  a item associated with the specified position of adapter
     *
     * @param position
     * @param item
     */
    public void add(int position, T item) {
        mData.add(position, item);
        notifyItemInserted(position);
    }

    public void add(T item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void setAllNewData(List<T> data) {
        if (data == null) {
            return;
        }
        mData.clear();
        mData.addAll(data);
        mLastPosition = -1;
        notifyDataSetChanged();
    }

    /**
     * setting up a new instance to data;
     *
     * @param data
     */
    public void setNewData(List<T> data) {
        this.mData = data;
        mLastPosition = -1;
        notifyDataSetChanged();
    }

    /**
     * additional data;
     *
     * @param data
     */
    public void addData(List<T> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }


    /**
     * Get the data of list
     *
     * @return
     */
    public List<T> getData() {
        return mData;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    public T getItem(int position) {
        return mData.get(position);
    }

    /**
     * if setHeadView will be return 1 if not will be return 0
     *
     * @return
     */
    public int getHeaderViewsCount() {
        return mHeaderLayout == null ? 0 : 1;
    }

    /**
     * if addHeaderView will be return 1, if not will be return 0
     */
    public int getHeaderLayoutCount() {
        return mHeaderLayout == null ? 0 : 1;
    }

    /**
     * if mFooterView will be return 1 or not will be return 0
     *
     * @return
     */
    public int getFooterViewsCount() {
        return mFooterView == null ? 0 : 1;
    }

    /**
     * if mEmptyView will be return 1 or not will be return 0
     *
     * @return
     */
    public int getmEmptyViewCount() {
        return mEmptyView == null ? 0 : 1;
    }

    /**
     * returns the number of item that will be created
     *
     * @return
     */
    @Override
    public int getItemCount() {
        int i = isLoadMore() ? 1 : 0;
        int count = mData.size() + i + getHeaderViewsCount() + getFooterViewsCount();
        if (mData.size() == 0 && mEmptyView != null) {
            /**
             *  setEmptyView(false) and add emptyView
             */
            if (count == 0 && (!mHeadAndEmptyEnable || !mFootAndEmptyEnable)) {
                count += getmEmptyViewCount();
                /**
                 * {@link #setEmptyView(true, true, View)}
                 */
            } else if (mHeadAndEmptyEnable || mFootAndEmptyEnable) {
                count += getmEmptyViewCount();
            }

            if ((mHeadAndEmptyEnable && getHeaderViewsCount() == 1 && count == 1) || count == 0) {
                mEmptyEnable = true;
                count += getmEmptyViewCount();
            }

        }
        return count;
    }

    /**
     * Get the type of View that will be created by {@link #getItemView(int, ViewGroup)} for the specified item.
     *
     * @param position The position of the item within the adapter's data set whose view type we
     *                 want.
     * @return An integer representing the type of View. Two views should share the same type if one
     * can be converted to the other in {@link #getItemView(int, ViewGroup)}. Note: Integers must be in the
     * range 0 to {@link #getItemCount()} - 1.
     */
    @Override
    public int getItemViewType(int position) {
        /**
         * if set headView and positon =0
         */
        if (mHeaderLayout != null && position == 0) {
            return HEADER_VIEW;
        }
        /**
         * if user has no data and add emptyView and position <2{(headview +emptyView)}
         */
        if (mData.size() == 0 && mEmptyEnable && mEmptyView != null && position <= 2) {
            /**
             * if set {@link #setEmptyView(boolean, boolean, View)}  position = 1
             */
            if ((mHeadAndEmptyEnable || mFootAndEmptyEnable) && position == 1) {
                /**
                 * if user want to show headview and footview and emptyView but not add headview
                 */
                if (mHeaderLayout == null && mEmptyView != null && mFooterView != null) {
                    return FOOTER_VIEW;
                    /**
                     * add headview
                     */
                } else if (mHeaderLayout != null && mEmptyView != null) {
                    return EMPTY_VIEW;
                }
            } else if (position == 0) {
                /**
                 * has no emptyView just add emptyview
                 */
                if (mHeaderLayout == null) {
                    return EMPTY_VIEW;
                } else if (mFooterView != null)

                    return EMPTY_VIEW;


            } else if (position == 2 && (mFootAndEmptyEnable || mHeadAndEmptyEnable) && mHeaderLayout != null && mEmptyView != null) {
                return FOOTER_VIEW;

            } /**
             * user forget to set {@link #setEmptyView(boolean, boolean, View)}  but add footview and headview and emptyview
             */
            else if ((!mFootAndEmptyEnable || !mHeadAndEmptyEnable) && position == 1 && mFooterView != null) {
                return FOOTER_VIEW;
            }
        } else if (mData.size() == 0 && mEmptyView != null && getItemCount() == (mHeadAndEmptyEnable ? 2 : 1) && mEmptyEnable) {
            return EMPTY_VIEW;
        } else if (position == mData.size() + getHeaderViewsCount()) {
            if (mIsFooter) {
                return LOADING_VIEW;
            } else {
                return FOOTER_VIEW;
            }
        }
        return getDefItemViewType(position - getHeaderViewsCount());
    }

    protected int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = null;
        this.mContext = parent.getContext();
        this.mLayoutInflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case LOADING_VIEW:
                baseViewHolder = getLoadingView(parent);
                break;
            case HEADER_VIEW:
                baseViewHolder = new BaseViewHolder(mHeaderLayout);
                break;
            case EMPTY_VIEW:
                baseViewHolder = new BaseViewHolder(mEmptyView);
                break;
            case FOOTER_VIEW:
                baseViewHolder = new BaseViewHolder(mFooterView);
                break;
            default:
                baseViewHolder = onCreateDefViewHolder(parent, viewType);
                initItemClickListener(baseViewHolder);
        }
        return baseViewHolder;

    }


    private BaseViewHolder getLoadingView(ViewGroup parent) {

        return new BaseViewHolder(loadMoreContainer.getFooterView());
    }

    /**
     * Called when a view created by this adapter has been attached to a window.
     * simple to solve item will layout using all
     * {@link #setFullSpan(RecyclerView.ViewHolder)}
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW || type == LOADING_VIEW) {
            setFullSpan(holder);
        }
    }

    /**
     * Return root layout of header
     */
    public LinearLayout getHeaderLayout() {
        return mHeaderLayout;
    }

    /**
     * When set to true, the item will layout using all span area. That means, if orientation
     * is vertical, the view will have full width; if orientation is horizontal, the view will
     * have full height.
     * if the hold view use StaggeredGridLayoutManager they should using all span area
     *
     * @param holder True if this item should traverse all spans.
     */
    protected void setFullSpan(RecyclerView.ViewHolder holder) {
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    return (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW || type == LOADING_VIEW) ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * To bind different types of holder and solve different the bind events
     *
     * @param holder
     * @param positions
     * @see #getDefItemViewType(int)
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int positions) {
        int viewType = holder.getItemViewType();

        switch (viewType) {
            case 0:
                convert((BaseViewHolder) holder, mData.get(holder.getLayoutPosition() - getHeaderViewsCount()));
                addAnimation(holder);
                break;
            case LOADING_VIEW:
                loadMoreContainer.setOnScrollListener();
                break;
            case HEADER_VIEW:
                break;
            case EMPTY_VIEW:
                break;
            case FOOTER_VIEW:
                break;
            default:
                convert((BaseViewHolder) holder, mData.get(holder.getLayoutPosition() - getHeaderViewsCount()));
                onBindDefViewHolder((BaseViewHolder) holder, mData.get(holder.getLayoutPosition() - getHeaderViewsCount()));
                break;
        }

    }

    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, mLayoutResId);
    }

    protected BaseViewHolder createBaseViewHolder(ViewGroup parent, int layoutResId) {
        if (mContentView == null) {
            return new BaseViewHolder(getItemView(layoutResId, parent));
        }
        return new BaseViewHolder(mContentView);
    }

    /**
     * Append header to the rear of the mHeaderLayout.
     *
     * @param header
     */
    public void addHeaderView(View header) {
        addHeaderView(header, -1);
    }

    public void remoteHeadView() {
        if(mHeaderLayout != null) {
            mHeaderLayout = null;
            mCopyHeaderLayout = null;
        }
        notifyDataSetChanged();
    }

    /**
     * Add header view to mHeaderLayout and set header view position in mHeaderLayout.
     * When index = -1 or index >= child count in mHeaderLayout,
     * the effect of this method is the same as that of {@link #addHeaderView(View)}.
     *
     * @param header
     * @param index  the position in mHeaderLayout of this header.
     *               When index = -1 or index >= child count in mHeaderLayout,
     *               the effect of this method is the same as that of {@link #addHeaderView(View)}.
     */
    public void addHeaderView(View header, int index) {
        if (mHeaderLayout == null) {
            if (mCopyHeaderLayout == null) {
                mHeaderLayout = new LinearLayout(header.getContext());
                mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
                mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
                mCopyHeaderLayout = mHeaderLayout;
            } else {
                mHeaderLayout = mCopyHeaderLayout;
            }
        }
        index = index >= mHeaderLayout.getChildCount() ? -1 : index;
        mHeaderLayout.addView(header, index);
        this.notifyDataSetChanged();
    }

    public void addHeaderView(View header, int index, int w_parent, int h_parent) {
        if (mHeaderLayout == null) {
            if (mCopyHeaderLayout == null) {
                mHeaderLayout = new LinearLayout(header.getContext());
                mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
                mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
                mCopyHeaderLayout = mHeaderLayout;
            } else {
                mHeaderLayout = mCopyHeaderLayout;
            }
        }
        index = index >= mHeaderLayout.getChildCount() ? -1 : index;
        mHeaderLayout.addView(header, index);
        this.notifyDataSetChanged();
    }

    /**
     * easy to show a simple footerView
     *
     * @param footer
     */
    public void addFooterView(View footer) {
        mIsFooter = false;
        this.mFooterView = footer;
        this.notifyDataSetChanged();
    }

    /**
     * Sets the view to show if the adapter is empty
     */
    public void setEmptyView(View emptyView) {
        setEmptyView(false, false, emptyView);
    }

    /**
     * @param isHeadAndEmpty false will not show headView if the data is empty true will show emptyView and headView
     * @param emptyView
     */
    public void setEmptyView(boolean isHeadAndEmpty, View emptyView) {
        setEmptyView(isHeadAndEmpty, false, emptyView);
    }

    /**
     * set emptyView show if adapter is empty and want to show headview and footview
     *
     * @param isHeadAndEmpty
     * @param isFootAndEmpty
     * @param emptyView
     */
    public void setEmptyView(boolean isHeadAndEmpty, boolean isFootAndEmpty, View emptyView) {
        mHeadAndEmptyEnable = isHeadAndEmpty;
        mFootAndEmptyEnable = isFootAndEmpty;
        mEmptyView = emptyView;
        mEmptyEnable = true;
    }

    /**
     * When the current adapter is empty, the BaseQuickAdapter can display a special view
     * called the empty view. The empty view is used to provide feedback to the user
     * that no data is available in this AdapterView.
     *
     * @return The view to show if the adapter is empty.
     */
    public View getEmptyView() {
        return mEmptyView;
    }


    /**
     * @param isNextLoad true
     *                   if true when loading more data can show loadingView
     */
    public void notifyDataChangedAfterLoadMore(boolean isNextLoad) {
        notifyDataSetChanged();

    }

    /**
     * add more data
     *
     * @param data
     * @param isNextLoad
     */
    public void notifyDataChangedAfterLoadMore(List<T> data, boolean isNextLoad) {
        mData.addAll(data);
        notifyDataChangedAfterLoadMore(isNextLoad);

    }

    /**
     * init the baseViewHolder to register onRecyclerViewItemClickListener and onRecyclerViewItemLongClickListener
     *
     * @param baseViewHolder
     */
    private void initItemClickListener(final BaseViewHolder baseViewHolder) {
        if (onRecyclerViewItemClickListener != null) {
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerViewItemClickListener.onItemClick(v, baseViewHolder.getLayoutPosition() - getHeaderViewsCount());
                }
            });
        }
        if (onRecyclerViewItemLongClickListener != null) {
            baseViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onRecyclerViewItemLongClickListener.onItemLongClick(v, baseViewHolder.getLayoutPosition() - getHeaderViewsCount());
                }
            });
        }
    }

    /**
     * add animation when you want to show time
     *
     * @param holder
     */
    private void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (!mFirstOnlyEnable || holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = null;
                if (mCustomAnimation != null) {
                    animation = mCustomAnimation;
                } else {
                    animation = mSelectAnimation;
                }
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim, holder.getLayoutPosition());
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    /**
     * set anim to start when loading
     *
     * @param anim
     * @param index
     */
    protected void startAnim(Animator anim, int index) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
    }

    /**
     * Determine whether it is loaded more
     *
     * @return
     */
    private boolean isLoadMore() {
        return loadMoreContainer != null;
    }

    /**
     * @param layoutResId ID for an XML layout resource to load
     * @param parent      Optional view to be the parent of the generated hierarchy or else simply an object that
     *                    provides a set of LayoutParams values for root of the returned
     *                    hierarchy
     * @return view will be return
     */
    protected View getItemView(int layoutResId, ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }


    /**
     * @see #convert(BaseViewHolder, Object) ()
     * @deprecated This method is deprecated
     * {@link #convert(BaseViewHolder, Object)} depending on your use case.
     */
    @Deprecated
    protected void onBindDefViewHolder(BaseViewHolder holder, T item) {
    }

    /**
     * Set the view animation type.
     *
     * @param animationType One of {@link #ALPHAIN}, {@link #SCALEIN}, {@link #SLIDEIN_BOTTOM}, {@link #SLIDEIN_LEFT}, {@link #SLIDEIN_RIGHT}.
     */
    public void openLoadAnimation(@AnimationType int animationType) {
        this.mOpenAnimationEnable = true;
        mCustomAnimation = null;
        switch (animationType) {
            case ALPHAIN:
                mSelectAnimation = new AlphaInAnimation();
                break;
            case SCALEIN:
                mSelectAnimation = new ScaleInAnimation();
                break;
            case SLIDEIN_BOTTOM:
                mSelectAnimation = new SlideInBottomAnimation();
                break;
            case SLIDEIN_LEFT:
                mSelectAnimation = new SlideInLeftAnimation();
                break;
            case SLIDEIN_RIGHT:
                mSelectAnimation = new SlideInRightAnimation();
                break;
            default:
                break;
        }
    }

    /**
     * Set Custom ObjectAnimator
     *
     * @param animation ObjectAnimator
     */
    public void openLoadAnimation(BaseAnimation animation) {
        this.mOpenAnimationEnable = true;
        this.mCustomAnimation = animation;
    }

    /**
     * To open the animation when loading
     */
    public void openLoadAnimation() {
        this.mOpenAnimationEnable = true;
    }

    /**
     * {@link #addAnimation(RecyclerView.ViewHolder)}
     *
     * @param firstOnly true just show anim when first loading false show anim when load the data every time
     */
    public void isFirstOnly(boolean firstOnly) {
        this.mFirstOnlyEnable = firstOnly;
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    protected abstract void convert(BaseViewHolder helper, T item);

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * Get the parent item position of the IExpandable item
     *
     * @return return the closest parent item position of the IExpandable.
     * if the IExpandable item's level is 0, return itself position.
     * if the item's level is negative which mean do not implement this, return a negative
     * if the item is not exist in the data list, return a negative.
     */
    public int getParentPosition(@NonNull T item) {
        int position = getItemPosition(item);
        if (position == -1) {
            return -1;
        }

        // if the item is IExpandable, return a closest IExpandable item position whose level smaller than this.
        // if it is not, return the closest IExpandable item position whose level is not negative
        int level;
        if (item instanceof IExpandable) {
            level = ((IExpandable) item).getLevel();
        } else {
            level = Integer.MAX_VALUE;
        }
        if (level == 0) {
            return position;
        } else if (level == -1) {
            return -1;
        }

        for (int i = position; i >= 0; i--) {
            T temp = mData.get(i);
            if (temp instanceof IExpandable) {
                IExpandable expandable = (IExpandable) temp;
                if (expandable.getLevel() >= 0 && expandable.getLevel() < level) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int getItemPosition(T item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }
}
