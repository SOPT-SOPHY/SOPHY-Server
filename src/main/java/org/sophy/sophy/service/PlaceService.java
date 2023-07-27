package org.sophy.sophy.service;

import lombok.RequiredArgsConstructor;
import org.sophy.sophy.controller.dto.response.PlaceResponseDto;
import org.sophy.sophy.domain.Member;
import org.sophy.sophy.domain.Place;
import org.sophy.sophy.domain.dto.place.request.PlaceRequestDto;
import org.sophy.sophy.domain.enumerate.Authority;
import org.sophy.sophy.domain.enumerate.City;
import org.sophy.sophy.exception.ErrorStatus;
import org.sophy.sophy.exception.model.ForbiddenException;
import org.sophy.sophy.external.client.aws.S3Service;
import org.sophy.sophy.infrastructure.MemberRepository;
import org.sophy.sophy.infrastructure.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    // 공간 생성
    @Transactional
    public PlaceResponseDto createPlace(PlaceRequestDto placeRequestDto, String email) {
        Member member = memberRepository.getMemberByEmail(email);
        if (!member.getAuthority().equals(Authority.ROLE_OPERATOR)) {
            throw new ForbiddenException(ErrorStatus.FORBIDDEN_USER_EXCEPTION, ErrorStatus.FORBIDDEN_USER_EXCEPTION.getMessage());
        }
        String placeImageUrl = null;
        if (!placeRequestDto.getPlaceImage().isEmpty())
            placeImageUrl = s3Service.uploadImage(placeRequestDto.getPlaceImage(), "image");
        Place place = placeRequestDto.toPlace(member, placeImageUrl);
        return PlaceResponseDto.of(placeRepository.save(place));
    }

    public List<PlaceResponseDto> getPlacesByCity(City city) {
        //의정부 시이면 전체 조회
        if (city == City.UIJEONGBU_SI) {
            return placeRepository.findAll().stream()
                    .map(PlaceResponseDto::of)
                    .collect(Collectors.toList());
        }
        return placeRepository.findAllByCity(city).stream()
                .map(PlaceResponseDto::of)
                .collect(Collectors.toList());
    }
}
