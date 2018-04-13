package io.github.groupease.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelInvitationCreateWrapper {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Channel {
        public long id;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Recipient {
        public long id;
    }

    public Channel channel;
    public Recipient recipient;
}
