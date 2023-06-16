package com.ticketing.domain.venue.service

import com.ticketing.domain.venue.dto.VenueCreateReq
import com.ticketing.domain.venue.entity.Venue
import com.ticketing.domain.venue.repository.VenueRepository
import spock.lang.Specification

class VenueServiceTest extends Specification {

    VenueService venueService;
    def venueRepository

    void setup() {
        venueRepository = Mock(VenueRepository)
        venueService = new VenueService(venueRepository)
    }


    def "Creat"() {
        given:
        def venueCreateReq = new VenueCreateReq(
                "용인 포은아트홀",
                "경기도 용인시 죽전2동",
                "0310000000",
                500
        );
        when:
        venueService.creat(venueCreateReq)
        then:
        venueRepository.save(_) >> venueCreateReq.toEntity()

    }

    def "GetBy"() {
        given:
        def venueId = 1L;
        def venue = new Venue("용인 포은아트홀",
                "경기도 용인시 죽전2동",
                "0310000000",
                500)
        when:
        venueService.getBy(venueId)

        then:
        venueRepository.findById(_) >> Optional.of(venue)
    }


}
