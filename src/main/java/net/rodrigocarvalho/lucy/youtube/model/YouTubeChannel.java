/*
 * Copyright (c) Rodrigo Carvalho (Duck)
 */

package net.rodrigocarvalho.lucy.youtube.model;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.ThumbnailDetails;

import java.math.BigInteger;

/**
 * The type You tube channel.
 */
public class YouTubeChannel {

    private Channel channel;

    /**
     * Instantiates a new You tube channel.
     *
     * @param channel the channel
     */
    public YouTubeChannel(Channel channel) {
        this.channel = channel;
    }

    /**
     * Gets views.
     *
     * @return the views
     */
    public BigInteger getViews() {
        return channel.getStatistics().getViewCount();
    }

    /**
     * Gets comments.
     *
     * @return the comments
     */
    public BigInteger getComments() {
        return channel.getStatistics().getCommentCount();
    }

    /**
     * Gets videos.
     *
     * @return the videos
     */
    public BigInteger getVideos() {
        return channel.getStatistics().getVideoCount();
    }

    /**
     * Gets subscribers.
     *
     * @return the subscribers
     */
    public BigInteger getSubscribers() {
        return channel.getStatistics().getSubscriberCount();
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return channel.getSnippet().getTitle();
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return channel.getSnippet().getDescription();
    }

    /**
     * Gets created time.
     *
     * @return the created time
     */
    public DateTime getCreatedTime() {
        return channel.getSnippet().getPublishedAt();
    }

    /**
     * Gets thumbnails.
     *
     * @return the thumbnails
     */
    public ThumbnailDetails getThumbnails() {
        return channel.getSnippet().getThumbnails();
    }

    /**
     * Gets default thumbnail.
     *
     * @return the default thumbnail
     */
    public Thumbnail getDefaultThumbnail() {
        return getThumbnails().getDefault();
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return channel.getSnippet().getCustomUrl();
    }
}