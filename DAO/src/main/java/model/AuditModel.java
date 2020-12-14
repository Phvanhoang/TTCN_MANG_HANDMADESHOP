package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt", "updatedBy", "createdBy", "deleted"},
        allowGetters = true
)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditModel<U> implements Serializable {
	private static final long serialVersionUID = 4927595793802724713L;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedAt", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdatedAt", nullable = true)
    @LastModifiedDate
    private Date updatedAt;

	@JoinColumn(name = "UpdatedBy", nullable = true)
	@LastModifiedBy
	@ManyToOne
	private U updatedBy;

    @JoinColumn(name = "CreatedBy", nullable = true, updatable = false)
    @CreatedBy
    @ManyToOne()
    private U createdBy;

    @JoinColumn(name = "Deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public U getCreatedBy() {
		return createdBy;
	}
    
    public void setCreatedBy(U createdBy) {
		this.createdBy = createdBy;
	}
    
    public boolean isDeleted() {
		return deleted;
	}
    
    public void setDeleted(boolean isDeleted) {
		this.deleted = isDeleted;
	}
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public U getUpdatedBy() {
		return updatedBy;
	}
    
    public void setUpdatedBy(U updatedBy) {
		this.updatedBy = updatedBy;
	}
}