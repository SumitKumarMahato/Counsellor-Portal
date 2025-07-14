package com.project.counsellor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.project.counsellor.dto.DashboardResponseDto;
import com.project.counsellor.dto.EnqFilterDto;
import com.project.counsellor.dto.EnquiryDto;
import com.project.counsellor.entities.CounsellorEntity;
import com.project.counsellor.entities.EnquiryEntity;
import com.project.counsellor.repository.CounsellorRepo;
import com.project.counsellor.repository.EnquiryRepository;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private EnquiryRepository enquiryRepo;
	@Autowired
	private CounsellorRepo counsellorRepo;

	@Override
	public DashboardResponseDto getDashboardInfo(Integer counsellorId) {
		DashboardResponseDto dto = new DashboardResponseDto();
		List<EnquiryEntity> enqList = enquiryRepo.findByCounsellorCounsellorId(counsellorId);

		int openCount = enqList.stream().filter(enq -> enq.getEnqStatus().equalsIgnoreCase("OPEN"))
				.collect(Collectors.toList()).size();

		int enrolledCount = enqList.stream().filter(enq -> enq.getEnqStatus().equalsIgnoreCase("ENROLLED"))
				.collect(Collectors.toList()).size();

		int lostCount = enqList.stream().filter(enq -> enq.getEnqStatus().equalsIgnoreCase("LOST"))
				.collect(Collectors.toList()).size();

		dto.setTotalEnqCount(enqList.size());
		dto.setOpenEnqCount(openCount);
		dto.setEnrolledEnqcount(enrolledCount);
		dto.setLostEnqCount(lostCount);
		return dto;
	}

	@Override
	public boolean addEnquiry(EnquiryDto enqDto, Integer counsellorId) {

		EnquiryEntity enqEntity = new EnquiryEntity();

		BeanUtils.copyProperties(enqDto, enqEntity);

		Optional<CounsellorEntity> byId = counsellorRepo.findById(counsellorId);
		if (byId.isPresent()) {
			CounsellorEntity counsellor = byId.get();
			enqEntity.setCounsellor(counsellor);
		}

		EnquiryEntity savedEntity = enquiryRepo.save(enqEntity);
		return savedEntity.getEnqId() != null;
	}

	@Override
	public List<EnquiryDto> getEnquiries(Integer counsellorId) {

		List<EnquiryDto> enqDtoList = new ArrayList<>();
		List<EnquiryEntity> enqList = enquiryRepo.findByCounsellorCounsellorId(counsellorId);

		for (EnquiryEntity entity : enqList) {

			EnquiryDto dto = new EnquiryDto();
			BeanUtils.copyProperties(entity, dto);

			enqDtoList.add(dto);
		}
		return enqDtoList;
	}

	@Override
	public List<EnquiryDto> getEnquiries(EnqFilterDto filterDTO, Integer counsellorId) {
		EnquiryEntity enqEntity = new EnquiryEntity();

		if (filterDTO.getClassMode() != null && !filterDTO.getClassMode().equals("")) {
			enqEntity.setClassMode(filterDTO.getClassMode());
		}

		if (filterDTO.getCourse() != null && !filterDTO.getCourse().equals("")) {
			enqEntity.setCourse(filterDTO.getCourse());
		}

		if (filterDTO.getEnqStatus() != null && !filterDTO.getEnqStatus().equals("")) {
			enqEntity.setEnqStatus(filterDTO.getEnqStatus());
		}

		CounsellorEntity counsellor = new CounsellorEntity();
		counsellor.setCounsellorId(counsellorId);
		enqEntity.setCounsellor(counsellor);

		Example<EnquiryEntity> of = Example.of(enqEntity);
		List<EnquiryEntity> enqList = enquiryRepo.findAll(of);

		List<EnquiryDto> enqsDtoList = new ArrayList<>();
		for (EnquiryEntity enq : enqList) {
			EnquiryDto enqDto = new EnquiryDto();
			BeanUtils.copyProperties(enq, enqDto);
			enqsDtoList.add(enqDto);
		}
		return enqsDtoList;
	}

	@Override
	public EnquiryDto getEnquiryById(Integer enqId) {
		Optional<EnquiryEntity> byId = enquiryRepo.findById(enqId);
		if (byId.isPresent()) {
			EnquiryEntity enquiryEntity = byId.get();
			EnquiryDto dto = new EnquiryDto();
			BeanUtils.copyProperties(enquiryEntity, dto);

			return dto;
		}

		return null;
	}

	@Override
	public boolean updateEnquiry(Integer enqId, EnquiryDto enqDto, Integer counsellorId) {

		EnquiryDto dto = getEnquiryById(enqId);
		dto.setEnqId(enqDto.getEnqId());
		dto.setStudentName(enqDto.getStudentName());
		dto.setStudentPhno(enqDto.getStudentPhno());
		dto.setClassMode(enqDto.getClassMode());
		dto.setCourse(enqDto.getCourse());
		dto.setEnqStatus(enqDto.getEnqStatus());

		EnquiryEntity entity = new EnquiryEntity();
		BeanUtils.copyProperties(dto, entity);

		Optional<CounsellorEntity> byId = counsellorRepo.findById(counsellorId);

		entity.setCounsellor(byId.get());
		EnquiryEntity save = enquiryRepo.save(entity);

		return save.getEnqId() != null;
	}

	@Override
	public boolean deleteEnquiryById(Integer enqId) {

		if (enquiryRepo.existsById(enqId)) {
			enquiryRepo.deleteById(enqId);

			return true;
		}

		return false;
	}

}
