package com.project.counsellor.dto;

import lombok.Data;

@Data
public class DashboardResponseDto {

	private Integer totalEnqCount;
	private Integer openEnqCount;
	private Integer enrolledEnqcount;
	private Integer lostEnqCount;
	
}
