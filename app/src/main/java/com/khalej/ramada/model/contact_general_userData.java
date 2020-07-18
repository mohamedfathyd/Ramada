package com.khalej.ramada.model;
import com.google.gson.annotations.SerializedName;


public class contact_general_userData {
    @SerializedName("payload")
    user payload;
    @SerializedName("status")
    boolean status;
    @SerializedName("messages")
    String messages;
    @SerializedName("code")
    int code;

    public user getPayload() {
        return payload;
    }

    public void setPayload(user payload) {
        this.payload = payload;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public class about{
        @SerializedName("about")
        contact_about about;
        @SerializedName("social")
        contact_social social;

        public contact_social getSocial() {
            return social;
        }

        public void setSocial(contact_social social) {
            this.social = social;
        }

        public contact_about getAbout() {
            return about;
        }

        public void setAbout(contact_about about) {
            this.about = about;
        }
    }
    public class contact_about{
        @SerializedName("about_us")
        String about_us;
        @SerializedName("terms_and_conditions,")
        String terms_and_conditions;

        public String getAbout_us() {
            return about_us;
        }

        public void setAbout_us(String about_us) {
            this.about_us = about_us;
        }

        public String getTerms_and_conditions() {
            return terms_and_conditions;
        }

        public void setTerms_and_conditions(String terms_and_conditions) {
            this.terms_and_conditions = terms_and_conditions;
        }
    }
    public class contact_social{
        @SerializedName("facebook")
        String facebook;
        @SerializedName("twitter")
        String twitter;
        @SerializedName("linkedin")
        String linkedin;
        @SerializedName("instagram")
        String instagram;
        @SerializedName("youtube")
        String youtube;

        public String getFacebook() {
            return facebook;
        }

        public void setFacebook(String facebook) {
            this.facebook = facebook;
        }

        public String getTwitter() {
            return twitter;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        public String getLinkedin() {
            return linkedin;
        }

        public void setLinkedin(String linkedin) {
            this.linkedin = linkedin;
        }

        public String getInstagram() {
            return instagram;
        }

        public void setInstagram(String instagram) {
            this.instagram = instagram;
        }

        public String getYoutube() {
            return youtube;
        }

        public void setYoutube(String youtube) {
            this.youtube = youtube;
        }
    }
}
