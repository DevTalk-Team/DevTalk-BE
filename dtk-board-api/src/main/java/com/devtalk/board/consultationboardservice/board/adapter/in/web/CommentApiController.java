package com.devtalk.board.consultationboardservice.board.adapter.in.web;

import com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.CommentInput;
import com.devtalk.board.consultationboardservice.board.application.port.in.CommentUseCase;
import com.devtalk.board.consultationboardservice.board.application.port.in.dto.MemberRes;
import com.devtalk.board.consultationboardservice.board.application.port.out.MemberUseCase;
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

import static com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.CommentInput.*;

@Tag(name = "게시판 댓글", description = "게시판 댓글 API")
@RestController
@RequestMapping("/board/comment")
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentUseCase commentUseCase;
    private final MemberUseCase memberUseCase;

    @Operation(summary = "게시판 댓글 - 댓글 작성 API", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseWithoutResult.class)))
    })
    @PostMapping
    public ResponseEntity<?> createComment(@RequestHeader("User-Email") String email,
                                           @RequestBody CommentCreationInput commentCreationInput) {
        MemberRes.ProfileRes user = memberUseCase.findUser(email);
        commentUseCase.createComment(commentCreationInput.toReq(user.getId(), user.getName()));
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

    @Operation(summary = "게시판 댓글 - 게시글 댓글 수정 API", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseWithoutResult.class)))
    })
    @PutMapping
    public ResponseEntity<?> modifyComment(@RequestHeader("User-Email") String email,
                                           @RequestBody CommentModifyInput commentModifyInput) {
        MemberRes.ProfileRes user = memberUseCase.findUser(email);
        commentUseCase.modifyComment(commentModifyInput.toReq(user.getId()));
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.MODIFY_COMMENT_SUCCESS);
    }

    @Operation(summary = "게시판 댓글 - 게시글 댓글 삭제 API", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseWithoutResult.class)))
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@RequestHeader("User-Email") String email,
                                           @PathVariable Long commentId) {
        MemberRes.ProfileRes user = memberUseCase.findUser(email);
        commentUseCase.deleteComment(user.getId(), commentId);
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.DELETE_COMMENT_SUCCESS);
    }
}
