/*
 * Copyright (c) Rodrigo Carvalho (Duck)
 */

package net.rodrigocarvalho.lucy.youtube.model;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.api.services.youtube.model.ThumbnailDetails;
import com.google.api.services.youtube.model.Video;
import net.rodrigocarvalho.lucy.youtube.YoutubeAPI;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * The type You tube video.
 */
public class YouTubeVideo  {

    private Video video;
    private YouTubeChannel channel;

    /**
     * Instantiates a new You tube video.
     *
     * @param video the video
     * @param youtubeAPI instance
     */
    public YouTubeVideo(Video video, YoutubeAPI youtubeAPI) {
        this.video = video;
        try {
            this.channel = youtubeAPI.getChannelsByChannelId(this.video.getSnippet().getChannelId()).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Instantiates a new You tube video.
     *
     * @param video the video
     * @param channel the channel of author
     */
    public YouTubeVideo(Video video, YouTubeChannel channel) {
        this.video = video;
        this.channel = channel;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return video.getSnippet().getTitle();
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return video.getSnippet().getDescription();
    }

    /**
     * Gets views.
     *
     * @return the views
     */
    public BigInteger getViews() {
        return video.getStatistics().getViewCount();
    }

    /**
     * Gets comments.
     *
     * @return the comments
     */
    public BigInteger getComments() {
        return video.getStatistics().getCommentCount();
    }

    /**
     * Gets thumbnails.
     *
     * @return the thumbnails
     */
    public ThumbnailDetails getThumbnails() {
        return video.getSnippet().getThumbnails();
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
        return video.getSnippet().getPublishedAt();
    }

    /**
     * Gets duration ms.
     *
     * @return the duration ms
     */
    public String getDurationMs() {
        return video.getContentDetails().getDuration();
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public String getDuration() {
        String duration = getDurationMs();
        return convertYouTubeDuration(duration);
    }

    private String convertYouTubeDuration(String duration) {
        String youtubeDuration = duration;
        Calendar c = new GregorianCalendar();
        try {
            DateFormat df = new SimpleDateFormat("'PT'mm'M'ss'S'");
            Date d = df.parse(youtubeDuration);
            c.setTime(d);
        } catch (ParseException e) {
            try {
                DateFormat df = new SimpleDateFormat("'PT'hh'H'mm'M'ss'S'");
                Date d = df.parse(youtubeDuration);
                c.setTime(d);
            } catch (ParseException e1) {
                try {
                    DateFormat df = new SimpleDateFormat("'PT'ss'S'");
                    Date d = df.parse(youtubeDuration);
                    c.setTime(d);
                } catch (ParseException e2) {
                }
            }
        }
        c.setTimeZone(TimeZone.getDefault());

        String time = "";
        if ( c.get(Calendar.HOUR) > 0 ) {
            if ( String.valueOf(c.get(Calendar.HOUR)).length() == 1 ) {
                time += "0" + c.get(Calendar.HOUR);
            }
            else {
                time += c.get(Calendar.HOUR);
            }
            time += ":";
        }
        // test minute
        if ( String.valueOf(c.get(Calendar.MINUTE)).length() == 1 ) {
            time += "0" + c.get(Calendar.MINUTE);
        }
        else {
            time += c.get(Calendar.MINUTE);
        }
        time += ":";
        // test second
        if ( String.valueOf(c.get(Calendar.SECOND)).length() == 1 ) {
            time += "0" + c.get(Calendar.SECOND);
        }
        else {
            time += c.get(Calendar.SECOND);
        }
        return time ;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return "https://www.youtube.com/watch?v=" + this.video.getId();
    }

    /**
     * Gets likes.
     *
     * @return the likes
     */
    public BigInteger getLikes() {
        return video.getStatistics().getLikeCount();
    }

    /**
     * Gets dislikes.
     *
     * @return the dislikes
     */
    public BigInteger getDislikes() {
        return video.getStatistics().getDislikeCount();
    }

    /**
     * Gets favorites.
     *
     * @return the favorites
     */
    public BigInteger getFavorites() {
        return video.getStatistics().getFavoriteCount();
    }


    /**
     * Gets channel.
     *
     * @return the channel
     */
    public YouTubeChannel getChannel() {
        return channel;
    }
}