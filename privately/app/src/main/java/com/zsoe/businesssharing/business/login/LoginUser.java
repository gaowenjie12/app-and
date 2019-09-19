package com.zsoe.businesssharing.business.login;

/**
 * @author 于长亮   & E-mail: yuchangl3757@qq.com
 * @create_time 创建时间：2019-08-28 20:16
 * @version:
 * @类说明:
 */
public class LoginUser {
    private int id;
    private String username;
    private String nickname;
    private String email;
    private String mobile;
    private String avatar;
    private int gender;
    private String birthday;
    private int score;
    private int type;
    private String realname;
    private String uuid;
    private String district;
    private int serviceid;
    private String token;
    private int user_id;
    private long createtime;
    private long expiretime;
    private long expires_in;
    private String identify;
    private String companylocation;
    private int industry_pcate;
    private int industry_ccate;
    private String industry_pname;
    private String industry_cname;
    private String servicename;

    public String getIndustry_pname() {
        return industry_pname;
    }

    public void setIndustry_pname(String industry_pname) {
        this.industry_pname = industry_pname;
    }

    public String getIndustry_cname() {
        return industry_cname;
    }

    public void setIndustry_cname(String industry_cname) {
        this.industry_cname = industry_cname;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRealname() {
        return realname;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return district;
    }

    public void setServiceid(int serviceid) {
        this.serviceid = serviceid;
    }

    public int getServiceid() {
        return serviceid;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setExpiretime(long expiretime) {
        this.expiretime = expiretime;
    }

    public long getExpiretime() {
        return expiretime;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getIdentify() {
        return identify;
    }

    public void setCompanylocation(String companylocation) {
        this.companylocation = companylocation;
    }

    public String getCompanylocation() {
        return companylocation;
    }

    public void setIndustry_pcate(int industry_pcate) {
        this.industry_pcate = industry_pcate;
    }

    public int getIndustry_pcate() {
        return industry_pcate;
    }

    public void setIndustry_ccate(int industry_ccate) {
        this.industry_ccate = industry_ccate;
    }

    public int getIndustry_ccate() {
        return industry_ccate;
    }


    @Override
    public String toString() {
        return "LoginUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gender=" + gender +
                ", birthday='" + birthday + '\'' +
                ", score=" + score +
                ", type=" + type +
                ", realname='" + realname + '\'' +
                ", uuid='" + uuid + '\'' +
                ", district='" + district + '\'' +
                ", serviceid=" + serviceid +
                ", token='" + token + '\'' +
                ", user_id=" + user_id +
                ", createtime=" + createtime +
                ", expiretime=" + expiretime +
                ", expires_in=" + expires_in +
                ", identify='" + identify + '\'' +
                ", companylocation='" + companylocation + '\'' +
                ", industry_pcate=" + industry_pcate +
                ", industry_ccate=" + industry_ccate +
                ", industry_pname='" + industry_pname + '\'' +
                ", industry_cname='" + industry_cname + '\'' +
                ", servicename='" + servicename + '\'' +
                '}';
    }
}