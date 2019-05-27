package com.cmcc.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * DubboCoreProperties
 *
 * @author YL-S
 * @Create 2018/11/15
 */
@ConfigurationProperties(
        prefix = "dubbo"
)
public class DubboCoreProperties {

    private Integer port = Integer.valueOf(-1);
    private Integer timeout = Integer.valueOf(1000);
    private Integer threads = Integer.valueOf(2);
    private Integer heartBeats = Integer.valueOf(30000);
    private String host;
    private String serialization;
    private String version;
    private String mode = "provider";
    private String registry = "";

    public String getRegistry() {
        return this.registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public DubboCoreProperties() {
    }

    public Integer getPort() {
        return this.port;
    }

    public Integer getTimeout() {
        return this.timeout;
    }

    public Integer getThreads() {
        return this.threads;
    }

    public Integer getHeartBeats() {
        return this.heartBeats;
    }

    public String getHost() {
        return this.host;
    }

    public String getSerialization() {
        return this.serialization;
    }

    public String getVersion() {
        return this.version;
    }

    public String getMode() {
        return this.mode;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public void setHeartBeats(Integer heartBeats) {
        this.heartBeats = heartBeats;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setSerialization(String serialization) {
        this.serialization = serialization;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DubboCoreProperties)) {
            return false;
        } else {
            DubboCoreProperties other = (DubboCoreProperties)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Integer this$port = this.getPort();
                Integer other$port = other.getPort();
                if (this$port == null) {
                    if (other$port != null) {
                        return false;
                    }
                } else if (!this$port.equals(other$port)) {
                    return false;
                }

                Integer this$heartBeats = this.getTimeout();
                Integer other$heartBeats = other.getTimeout();
                if (this$heartBeats == null) {
                    if (other$heartBeats != null) {
                        return false;
                    }
                } else if (!this$heartBeats.equals(other$heartBeats)) {
                    return false;
                }

                label90: {
                    this$heartBeats = this.getThreads();
                    other$heartBeats = other.getThreads();
                    if (this$heartBeats == null) {
                        if (other$heartBeats == null) {
                            break label90;
                        }
                    } else if (this$heartBeats.equals(other$heartBeats)) {
                        break label90;
                    }

                    return false;
                }

                this$heartBeats = this.getHeartBeats();
                other$heartBeats = other.getHeartBeats();
                if (this$heartBeats == null) {
                    if (other$heartBeats != null) {
                        return false;
                    }
                } else if (!this$heartBeats.equals(other$heartBeats)) {
                    return false;
                }

                String this$version = this.getHost();
                String other$version = other.getHost();
                if (this$version == null) {
                    if (other$version != null) {
                        return false;
                    }
                } else if (!this$version.equals(other$version)) {
                    return false;
                }

                this$version = this.getSerialization();
                other$version = other.getSerialization();
                if (this$version == null) {
                    if (other$version != null) {
                        return false;
                    }
                } else if (!this$version.equals(other$version)) {
                    return false;
                }

                this$version = this.getVersion();
                other$version = other.getVersion();
                if (this$version == null) {
                    if (other$version != null) {
                        return false;
                    }
                } else if (!this$version.equals(other$version)) {
                    return false;
                }

                String this$mode = this.getMode();
                String other$mode = other.getMode();
                if (this$mode == null) {
                    if (other$mode != null) {
                        return false;
                    }
                } else if (!this$mode.equals(other$mode)) {
                    return false;
                }

                return true;
            }
        }
    }

    public boolean canEqual(Object other) {
        return other instanceof DubboCoreProperties;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        Integer $port = this.getPort();
        int result1 = result * 31 + ($port == null ? 0 : $port.hashCode());
        Integer $timeout = this.getTimeout();
        result1 = result1 * 31 + ($timeout == null ? 0 : $timeout.hashCode());
        Integer $threads = this.getThreads();
        result1 = result1 * 31 + ($threads == null ? 0 : $threads.hashCode());
        Integer $heartBeats = this.getHeartBeats();
        result1 = result1 * 31 + ($heartBeats == null ? 0 : $heartBeats.hashCode());
        String $host = this.getHost();
        result1 = result1 * 31 + ($host == null ? 0 : $host.hashCode());
        String $serialization = this.getSerialization();
        result1 = result1 * 31 + ($serialization == null ? 0 : $serialization.hashCode());
        String $version = this.getVersion();
        result1 = result1 * 31 + ($version == null ? 0 : $version.hashCode());
        String $mode = this.getMode();
        result1 = result1 * 31 + ($mode == null ? 0 : $mode.hashCode());
        return result1;
    }

    public String toString() {
        return "DubboCoreProperties(port=" + this.getPort() + ", timeout=" + this.getTimeout() + ", threads=" + this.getThreads() + ", heartBeats=" + this.getHeartBeats() + ", host=" + this.getHost() + ", serialization=" + this.getSerialization() + ", version=" + this.getVersion() + ", mode=" + this.getMode() + ")";
    }
}
