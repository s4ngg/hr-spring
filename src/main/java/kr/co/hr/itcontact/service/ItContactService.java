package kr.co.hr.itcontact.service;

import java.util.List;

import kr.co.hr.itcontact.dto.ItContactRequestDto;
import kr.co.hr.itcontact.dto.ItContactResponseDto;

public interface ItContactService {

    void createContact(ItContactRequestDto dto);

    List<ItContactResponseDto> getContactList();
}