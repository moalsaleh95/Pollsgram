package oth.ics.wtp.pollsgram.services;

import oth.ics.wtp.pollsgram.dtos.PollCreateRequest;
import oth.ics.wtp.pollsgram.dtos.PollResponse;
import oth.ics.wtp.pollsgram.dtos.PollUpdateRequest;
import oth.ics.wtp.pollsgram.entities.Poll;
import oth.ics.wtp.pollsgram.entities.Question;
import oth.ics.wtp.pollsgram.entities.User;
import oth.ics.wtp.pollsgram.exceptions.BadRequestException;
import oth.ics.wtp.pollsgram.exceptions.ForbiddenException;
import oth.ics.wtp.pollsgram.exceptions.ResourceNotFoundException;
import oth.ics.wtp.pollsgram.mappers.PollMapper;
import oth.ics.wtp.pollsgram.mappers.QuestionMapper;
import oth.ics.wtp.pollsgram.repositories.PollRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class PollService {

    private final PollRepository pollRepository;
    private final UserService userService;

    public PollService(
            PollRepository pollRepository,
            UserService userService
    ) {
        this.pollRepository = pollRepository;
        this.userService = userService;
    }

    public PollResponse createPoll(PollCreateRequest request, String username) {
        validateCreateRequest(request);

        if (pollRepository.existsByTitle(request.getTitle())) {
            throw new BadRequestException("Poll title already exists");
        }

        User creator = userService.findEntityByUsername(username);

        Poll poll = new Poll(
                request.getTitle(),
                request.getDescription(),
                request.getDueDate(),
                creator
        );

        request.getQuestions().forEach(questionRequest -> {
            Question question = QuestionMapper.toEntity(questionRequest);
            poll.addQuestion(question);
        });

        Poll savedPoll = pollRepository.save(poll);

        return PollMapper.toResponse(savedPoll);
    }

    public List<PollResponse> findMyPolls(String username) {
        User creator = userService.findEntityByUsername(username);

        return pollRepository.findByCreator(creator)
                .stream()
                .sorted(Comparator.comparing(Poll::getDueDate).reversed())
                .map(PollMapper::toResponse)
                .toList();
    }

    public List<PollResponse> findAvailablePolls(String username) {
        User currentUser = userService.findEntityByUsername(username);

        return pollRepository.findByFinishedFalse()
                .stream()
                .filter(poll -> !poll.getCreator().getId().equals(currentUser.getId()))
                .sorted(Comparator.comparing(Poll::getDueDate).reversed())
                .map(PollMapper::toResponse)
                .toList();
    }

    public PollResponse findById(Long id) {
        Poll poll = pollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Poll not found"));

        return PollMapper.toResponse(poll);
    }

    public Poll findEntityById(Long id) {
        return pollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Poll not found"));
    }

    public PollResponse finishPoll(Long pollId, String username) {
        Poll poll = findEntityById(pollId);
        User currentUser = userService.findEntityByUsername(username);

        if (!poll.getCreator().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("Only the poll creator can finish this poll");
        }

        if (poll.isFinished()) {
            throw new BadRequestException("Poll is already finished");
        }

        poll.setFinished(true);

        Poll savedPoll = pollRepository.save(poll);

        return PollMapper.toResponse(savedPoll);
    }

    public PollResponse updatePoll(Long pollId, PollUpdateRequest request, String username) {
        Poll poll = findEntityById(pollId);
        User currentUser = userService.findEntityByUsername(username);

        if (!poll.getCreator().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("Only the poll creator can update this poll");
        }

        if (poll.isFinished()) {
            throw new BadRequestException("Finished polls cannot be updated");
        }

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new BadRequestException("Poll title is required");
        }

        if (request.getDescription() == null || request.getDescription().isBlank()) {
            throw new BadRequestException("Poll description is required");
        }

        if (request.getDueDate() == null) {
            throw new BadRequestException("Due date is required");
        }

        if (request.getDueDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Due date cannot be in the past");
        }

        if (!poll.getTitle().equals(request.getTitle())
                && pollRepository.existsByTitle(request.getTitle())) {
            throw new BadRequestException("Poll title already exists");
        }

        poll.setTitle(request.getTitle());
        poll.setDescription(request.getDescription());
        poll.setDueDate(request.getDueDate());

        Poll savedPoll = pollRepository.save(poll);

        return PollMapper.toResponse(savedPoll);
    }

    public void deletePoll(Long pollId, String username) {
        Poll poll = findEntityById(pollId);
        User currentUser = userService.findEntityByUsername(username);

        if (!poll.getCreator().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("Only the poll creator can delete this poll");
        }

        pollRepository.delete(poll);
    }

    private void validateCreateRequest(PollCreateRequest request) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new BadRequestException("Poll title is required");
        }

        if (request.getDescription() == null || request.getDescription().isBlank()) {
            throw new BadRequestException("Poll description is required");
        }

        if (request.getDueDate() == null) {
            throw new BadRequestException("Due date is required");
        }

        if (request.getDueDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Due date cannot be in the past");
        }

        if (request.getQuestions() == null || request.getQuestions().isEmpty()) {
            throw new BadRequestException("A poll must contain at least one question");
        }

        request.getQuestions().forEach(question -> {
            if (question.getText() == null || question.getText().isBlank()) {
                throw new BadRequestException("Question text is required");
            }

            if (question.getType() == null) {
                throw new BadRequestException("Question type is required");
            }
        });
    }
}