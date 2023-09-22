package com.devtalk.board.consultationboardservice.board.adapter.in.web;

import com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.CommentInput;
import com.devtalk.board.consultationboardservice.board.application.port.in.CommentUseCase;
import com.devtalk.board.consultationboardservice.global.success.SuccessCode;
import com.devtalk.board.consultationboardservice.global.success.SuccessResponse;
import com.devtalk.board.consultationboardservice.global.success.SuccessResponseWithoutResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시판 댓글", description = "게시판 댓글 API")
@RestController
@RequestMapping("/board/comment")
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentUseCase commentUseCase;

    @Operation(summary = "게시판 댓글 - 댓글 작성 API", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseWithoutResult.class)))
    })
    @PostMapping("/{postId}")
    public ResponseEntity<?> createComment(@PathVariable Long postId, @RequestBody CommentInput commentInput) {
        commentUseCase.createComment(postId, commentInput);
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.CREATE_COMMENT_SUCCESS);
    }

    @Operation(summary = "게시판 댓글 - 댓글 단건 조회 API", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @GetMapping("/{commentId}")
    public ResponseEntity<SuccessResponse> getComment(@PathVariable Long commentId) {
        return SuccessResponse.toResponseEntity(SuccessCode.GET_COMMENT_SUCCESS, commentUseCase.getComment(commentId));
    }

    @Operation(summary = "게시판 댓글 - 게시글 댓글 전체 조회 API", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @GetMapping
    public ResponseEntity<SuccessResponse> getCommentsFromPost(@RequestParam("postId") Long postId) {
        return SuccessResponse.toResponseEntity(SuccessCode.GET_COMMENT_SUCCESS, commentUseCase.getCommentsFromPost(postId));
    }
}
