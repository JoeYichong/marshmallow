package yich.base.topic;


import yich.base.dbc.Require;
import yich.base.util.UrlInfo;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    protected String url;
    protected String username;
    protected String postTime;
    protected String postTitle;
    protected String postContent;

    private int weight = 0;
    private List<String> screenshots;

    public String getUrl() {
        return url;
    }

    public Topic setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Topic setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPostTime() {
        return postTime;
    }

    public Topic setPostTime(String post_time) {
        this.postTime = post_time;
        return this;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public Topic setPostTitle(String post_title) {
        this.postTitle = post_title;
        return this;
    }

    public String getPostContent() {
        return postContent;
    }

    public Topic setPostContent(String post_content) {
        this.postContent = post_content;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public Topic setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public List<String> getScreenshots() {
        return screenshots;
    }

    public void addScreenshot(String screenshot) {
        Require.argumentNotNullAndNotEmpty(screenshot, "String screenshot");
        if (screenshots == null) {
            screenshots = new ArrayList<>();
        }
        screenshots.add(screenshot);
        this.screenshots = screenshots;
    }

    @Override
    public String toString() {
        return "User\"" + username + "\"" + "("+ UrlInfo.of(url).domainName() +") Post:"
                + "(" + postTime + "): "
                + "\n[Title]：" + postTitle
                + "\n[Content]：" + postContent
                + "\n[Url]：" + url;
    }

}
