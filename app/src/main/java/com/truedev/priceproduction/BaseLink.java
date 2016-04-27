package com.truedev.priceproduction;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BaseLink implements Parcelable {
    public static final Creator<BaseLink> CREATOR = new Creator<BaseLink>() {
        public BaseLink createFromParcel(Parcel source) {
            return new BaseLink(source);
        }

        public BaseLink[] newArray(int size) {
            return new BaseLink[size];
        }
    };
    @SerializedName(value = "action")
    protected String action;
    @SerializedName("title")
    protected String title;
    @SerializedName("message")
    protected String message;
    @SerializedName("share_message")
    protected String shareMessage;
    @SerializedName("src")
    protected String linkSource;
    @SerializedName(value = "ids")
    private ArrayList<String> ids;

    public BaseLink(String action) {
        this.action = action;
    }

    public BaseLink() {
    }

    protected BaseLink(Parcel in) {
        this.action = in.readString();
        this.title = in.readString();
        this.message = in.readString();
        this.shareMessage = in.readString();
        this.linkSource = in.readString();
        this.ids = in.readArrayList(String.class.getClassLoader());
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getShareMessage() {
        return shareMessage;
    }

    public void setShareMessage(String shareMessage) {
        this.shareMessage = shareMessage;
    }

    public String getLinkSource() {
        return linkSource;
    }

    public void setLinkSource(String linkSource) {
        this.linkSource = linkSource;
    }



    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.action);
        dest.writeString(this.title);
        dest.writeString(this.message);
        dest.writeString(this.shareMessage);
        dest.writeString(this.linkSource);
        dest.writeList(this.ids);
    }
}
