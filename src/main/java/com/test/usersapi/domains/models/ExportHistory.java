package com.test.usersapi.domains.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "export_history")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class ExportHistory extends BaseModel {
	private static final long serialVersionUID = -3544096982719169693L;

    @Column(name = "file_name")
    private String fileName;
    
    @Column(name = "query")
    private String query;
    
    public ExportHistory(String fileName) {
    	super();
        this.fileName = fileName;
    }
    
    public ExportHistory(String fileName, String query) {
    	super();
        this.fileName = fileName;
        this.query = query;
    }

	public ExportHistory(String fileName, LocalDateTime now) {
    	super();
		this.fileName=fileName;
		this.createdAt=now;
		this.updatedAt=now;
	}

}


