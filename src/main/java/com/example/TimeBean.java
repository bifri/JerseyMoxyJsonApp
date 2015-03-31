package com.example;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class TimeBean {

    public String time;

    public TimeBean() {
    }

    public TimeBean(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TimeBean)) {
            return false;
        }
        TimeBean other = (TimeBean) obj;
        if (time == null) {
            if (other.time != null) {
                return false;
            }
        } else if (!time.equals(other.time)) {
            return false;
        }
        return true;
    }
}