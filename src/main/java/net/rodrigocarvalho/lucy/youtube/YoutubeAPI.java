/*
 * Copyright (c) Rodrigo Carvalho (Duck)
 */

package net.rodrigocarvalho.lucy.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import net.rodrigocarvalho.lucy.youtube.model.YouTubeChannel;
import net.rodrigocarvalho.lucy.youtube.model.YouTubePlaylist;
import net.rodrigocarvalho.lucy.youtube.model.YouTubeVideo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Youtube api.
 */
public class YoutubeAPI {

    private YouTube youTube;

    /**
     * Instantiates a new Youtube api.
     *
     * @throws IOException the io exception
     */
    public YoutubeAPI() throws IOException {
        this.youTube = YoutubeProvider.getYouTubeService();
    }

    /**
     * Gets channels by user name.
     *
     * @param userName the user name
     * @return the channels by user name
     * @throws IOException the io exception
     */
    public List<YouTubeChannel> getChannelsByUserName(String userName) throws IOException {
        YouTube.Channels.List channelsListByUsernameRequest = youTube.channels().list("snippet,contentDetails,statistics");
        channelsListByUsernameRequest.setForUsername(userName);
        List<YouTubeChannel> youTubeChannels = channelsListByUsernameRequest.execute().getItems().stream().map(YouTubeChannel::new).collect(Collectors.toList());
        return youTubeChannels;
    }

    /**
     * Gets channels by channel id.
     *
     * @param channelId the channel id
     * @return the channels by channel id
     * @throws IOException the io exception
     */
    public List<YouTubeChannel> getChannelsByChannelId(String channelId) throws IOException {
        YouTube.Channels.List channelsListByUsernameRequest = youTube.channels().list("snippet,contentDetails,statistics");
        channelsListByUsernameRequest.setId(channelId);
        List<YouTubeChannel> youTubeChannels = channelsListByUsernameRequest.execute().getItems().stream().map(YouTubeChannel::new).collect(Collectors.toList());
        return youTubeChannels;
    }

    /**
     * Gets videos by id.
     *
     * @param id the id
     * @return the videos by id
     * @throws IOException the io exception
     */
    public List<YouTubeVideo> getVideosById(String id) throws IOException {
        YouTube.Videos.List videoRequest = youTube.videos().list("snippet,statistics,contentDetails");
        videoRequest.setId(id);
        VideoListResponse result = videoRequest.execute();
        if (result.size() == 0) {
            return new ArrayList<>();
        }

        List<YouTubeVideo> youTubeVideos = result.getItems().stream().map(x -> {
            try {
                return new YouTubeVideo(x, getChannelsByChannelId(x.getSnippet().getChannelId()).get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        return youTubeVideos;
    }

    /**
     * Gets videos by id.
     *
     * @param ids the ids
     * @return the videos by id
     * @throws IOException the io exception
     */
    public List<YouTubeVideo> getVideosById(String... ids) throws IOException {
        List<YouTubeVideo> youTubeVideos = new ArrayList<>();
        for (String id : ids) {
            List<YouTubeVideo> result = getVideosById(id);
            if (result.size() > 0) {
                youTubeVideos.add(result.get(0));
            }
        }
        return youTubeVideos;
    }

    /**
     * Gets youtube playlist by id.
     *
     * @param id the id
     * @return the youtube playlist by id
     * @throws IOException the io exception
     */
    public List<YouTubePlaylist> getYoutubePlaylistById(String id) throws IOException {
        YouTube.Playlists.List playlistRequest = youTube.playlists().list("snippet,statistics,contentDetails");
        playlistRequest.setId(id);
        PlaylistListResponse result = playlistRequest.execute();
        if (result.size() == 0) {
            return new ArrayList<>();
        }

        List<YouTubePlaylist> youTubePlaylists = result.getItems().stream().map(YouTubePlaylist::new).collect(Collectors.toList());
        return youTubePlaylists;
    }

    /**
     * Gets top videos by views.
     *
     * @param maxResults the max results
     * @return the top videos by views
     * @throws IOException the io exception
     */
    public List<YouTubeVideo> getTopVideosByViews(long maxResults) throws IOException {
        YouTube.Search.List search = youTube.search().list("id,snippet");
        search.setType("video");
        search.setMaxResults(maxResults);
        search.setOrder("viewCount");
        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
        SearchListResponse searchResponse = search.execute();
        List<YouTubeVideo> youTubeVideos = new ArrayList<>();
        for (SearchResult searchResult : searchResponse.getItems()) {
            ResourceId id = searchResult.getId();
            if (id.getKind().equals("youtube#video")) {
                List<YouTubeVideo> videos = getVideosById(id.getVideoId());
                if (videos.size() > 0) {
                    youTubeVideos.addAll(videos);
                }
            }
        }
        return youTubeVideos;
    }

    /**
     * Gets top videos by likes.
     *
     * @param maxResults the max results
     * @return the top videos by likes
     * @throws IOException the io exception
     */
    public List<YouTubeVideo> getTopVideosByLikes(long maxResults) throws IOException {
        YouTube.Search.List search = youTube.search().list("id,snippet");
        search.setType("video");
        search.setMaxResults(maxResults);
        search.setOrder("rating");
        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
        SearchListResponse searchResponse = search.execute();
        List<YouTubeVideo> youTubeVideos = new ArrayList<>();
        for (SearchResult searchResult : searchResponse.getItems()) {
            ResourceId id = searchResult.getId();
            if (id.getKind().equals("youtube#video")) {
                List<YouTubeVideo> videos = getVideosById(id.getVideoId());
                if (videos.size() > 0) {
                    youTubeVideos.addAll(videos);
                }
            }
        }
        return youTubeVideos;
    }

    /**
     * Gets top channel by subscribers.
     *
     * @param maxResults the max results
     * @return the top channel by subscribers
     * @throws IOException the io exception
     */
    public List<YouTubeChannel> getTopChannelBySubscribers(long maxResults) throws IOException {
        YouTube.Search.List search = youTube.search().list("id,snippet");
        search.setType("channel");
        search.setMaxResults(maxResults);
        search.setOrder("rating");
        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
        SearchListResponse searchResponse = search.execute();
        List<YouTubeChannel> youTubeChannels = new ArrayList<>();
        for (SearchResult searchResult : searchResponse.getItems()) {
            ResourceId id = searchResult.getId();
            if (id.getKind().equals("youtube#channel")) {
                List<YouTubeChannel> channels = getChannelsByChannelId(id.getChannelId());
                if (youTubeChannels.size() > 0) {
                    youTubeChannels.addAll(channels);
                }
            }
        }
        return youTubeChannels;
    }

    /**
     * Search videos list.
     *
     * @param query      the query
     * @param maxResults the max results
     * @return the list
     * @throws IOException the io exception
     */
    public List<SearchResult> searchVideos(String query, long maxResults) throws IOException {
        YouTube.Search.List search = youTube.search().list("id,snippet");
        search.setType("video");
        search.setQ(query);

        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
        search.setOrder("viewCount");
        if (maxResults > 0) {
            search.setMaxResults(maxResults);
        } else {
            search.setMaxResults(50L);
        }
        SearchListResponse searchResponse = search.execute();
        return searchResponse.getItems();
    }
}