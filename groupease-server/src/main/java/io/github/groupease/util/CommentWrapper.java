package io.github.groupease.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Helper class that allows the system to bind incoming JSON with just
// a "comment field"
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentWrapper {
    public CommentWrapper() {}
    public String comments;
}
