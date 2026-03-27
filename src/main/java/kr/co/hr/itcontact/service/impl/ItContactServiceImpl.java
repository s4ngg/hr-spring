package kr.co.hr.itcontact.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.hr.itcontact.MailService;
import kr.co.hr.itcontact.dto.ItContactRequestDto;
import kr.co.hr.itcontact.dto.ItContactResponseDto;
import kr.co.hr.itcontact.entity.ItContact;
import kr.co.hr.itcontact.repository.ItContactRepository;
import kr.co.hr.itcontact.service.ItContactService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItContactServiceImpl implements ItContactService {

    private final ItContactRepository itContactRepository;
    private final MailService mailService;

    @Transactional
    @Override
    public void createContact(ItContactRequestDto dto) {
        itContactRepository.save(ItContact.of(dto));
        mailService.sendItContactMail(dto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItContactResponseDto> getContactList() {
        return ItContactResponseDto.fromList(itContactRepository.findAll());
    }
}