/*
 * Copyright (c) Rodrigo Carvalho (Duck)
 */

package com.duckdeveloper.lucy.youtube.model;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.ThumbnailDetails;

/**
 * The type You tube playlist.
 */
public class YouTubePlaylist {

    private Playlist playlist;

    /**
     * Instantiates a new You tube playlist.
     *
     * @param playlist the playlist
     */
    public YouTubePlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return playlist.getSnippet().getTitle();
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return playlist.getSnippet().getDescription();
    }

    /**
     * Gets thumbnails.
     *
     * @return the thumbnails
     */
    public ThumbnailDetails getThumbnails() {
        return playlist.getSnippet().getThumbnails();
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
     * Gets created time.
     *
     * @return the created time
     */
    public DateTime getCreatedTime() {
        return playlist.getSnippet().getPublishedAt();
    }

    /**
     * Gets videos.
     *
     * @return the videos
     */
    public long getVideos() {
        return playlist.getContentDetails().getItemCount();
    }
}