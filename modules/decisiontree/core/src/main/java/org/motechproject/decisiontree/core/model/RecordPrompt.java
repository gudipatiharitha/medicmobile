package org.motechproject.decisiontree.core.model;

public class RecordPrompt extends Prompt {

    /**
     * 
     */
    private static final long serialVersionUID = -1900180003763488208L;

    private String recordFileName;

    private String recordFormat;

    private Long maxDuration;
    
    private Long silence;

    public String getRecordFileName() {
        return recordFileName;
    }

    public RecordPrompt setRecordFileName(String recordFileName) {
        this.recordFileName = recordFileName;
        return this;
    }

    public String getRecordFormat() {
        return recordFormat;
    }

    public RecordPrompt setRecordFormat(String recordFormat) {
        this.recordFormat = recordFormat;
        return this;
    }

    public Long getMaxDuration() {
        return maxDuration;
    }

    public RecordPrompt setMaxDuration(Long maxDuration) {
        this.maxDuration = maxDuration;
        return this;
    }

    public Long getSilence() {
        return silence;
    }

    public RecordPrompt setSilence(Long silence) {
        this.silence = silence;
        return this;
    }

    @Override
    public String toString() {
        return "RecordPrompt fileName is " + this.recordFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        RecordPrompt that = (RecordPrompt) o;

        if (recordFileName != null ? !recordFileName
                .equals(that.recordFileName) : that.recordFileName != null) {
                        return false;
        }

        return true;
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result + (recordFileName != null ? recordFileName.hashCode() : 0) + (recordFormat != null ? recordFormat.hashCode() : 0);
        return result;
    }

}
