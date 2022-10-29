package kr.mashup.branding.service.generation;

import kr.mashup.branding.domain.ResultCode;
import kr.mashup.branding.domain.exception.BadRequestException;
import kr.mashup.branding.domain.exception.NotFoundException;
import kr.mashup.branding.domain.generation.Generation;
import kr.mashup.branding.domain.generation.exception.GenerationNotFoundException;
import kr.mashup.branding.repository.generation.GenerationRepository;
import kr.mashup.branding.service.generation.vo.GenerationCreateVo;
import kr.mashup.branding.service.generation.vo.GenerationUpdateVo;
import kr.mashup.branding.util.DateRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GenerationService {

    private final GenerationRepository generationRepository;

    public Generation getByIdOrThrow(Long generationId) {
        return generationRepository.findById(generationId)
            .orElseThrow(() -> new NotFoundException(ResultCode.GENERATION_NOT_FOUND));
    }

    public Generation getByNumberOrThrow(Integer number) {
        return generationRepository.findByNumber(number)
            .orElseThrow(() -> new NotFoundException(ResultCode.GENERATION_NOT_FOUND));
    }

    public List<Generation> getAll() {
        return generationRepository.findAll();
    }

    public Generation create(@Valid GenerationCreateVo createVo){

        final Integer generationNumber = createVo.getGenerationNumber();

        final boolean existsByNumber = generationRepository.existsByNumber(generationNumber);
        if(existsByNumber){
            throw new BadRequestException(ResultCode.GENERATION_ALREADY_EXISTS);
        }

        final DateRange generationDateRange
            = DateRange.of(createVo.getStatedAt(), createVo.getEndedAt());
        final Generation generation = Generation.of(generationNumber, generationDateRange);

        generationRepository.save(generation);

        return generation;
    }

    public Generation update(@Valid GenerationUpdateVo updateVo) {

        final Long generationId = updateVo.getGenerationId();

        final Generation generation = generationRepository
            .findById(generationId)
            .orElseThrow(GenerationNotFoundException::new);

        final DateRange generationDateRange
            = DateRange.of(updateVo.getStatedAt(), updateVo.getEndedAt());

        generation.changeDate(generationDateRange);

        return generation;
    }
}
