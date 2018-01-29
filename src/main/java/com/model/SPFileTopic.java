package com.model;


public class SPFileTopic extends LongIDEntity {
    private static final long serialVersionUID = 1L;
    private Long fileId;
    private Long topicId;
    private Long sort;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "SPFileTopic{" +
                "fileId=" + fileId +
                ", topicId=" + topicId +
                ", sort=" + sort +
                '}';
    }
}
