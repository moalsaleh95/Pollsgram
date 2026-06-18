package oth.ics.wtp.pollsgram.controllers;

import oth.ics.wtp.pollsgram.dtos.PollCreateRequest;
import oth.ics.wtp.pollsgram.dtos.PollResponse;
import oth.ics.wtp.pollsgram.services.PollService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    private final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PollResponse createPoll(
            @RequestBody PollCreateRequest request,
            Principal principal
    ) {
        return pollService.createPoll(request, principal.getName());
    }

    @GetMapping("/my")
    public List<PollResponse> getMyPolls(Principal principal) {
        return pollService.findMyPolls(principal.getName());
    }

    @GetMapping("/{id}")
    public PollResponse getPollById(@PathVariable Long id) {
        return pollService.findById(id);
    }
}