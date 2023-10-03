package com.devtalk.board.consultationboardservice.board.adapter.in.web;

import com.devtalk.board.consultationboardservice.board.application.port.in.PostUseCase;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.PostInput.*;

@Tag(name = "게시판", description = "게시판 API")
@RestController
@RequestMapping("/board/post")
@RequiredArgsConstructor
public class PostApiController {
    private final PostUseCase postUseCase;

    @Operation(summary = "게시판 - 게시글 작성 API", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseWithoutResult.class)))
    })
    @PostMapping
    public ResponseEntity<?> post(@RequestParam Long userId,
                                  @RequestParam String title,
                                  @RequestParam String content,
                                  @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        PostCreationInput postCreationInput = PostCreationInput.builder()
                .title(title)
                .content(content)
                .attachedFileList(files)
                .build();

        postUseCase.writePost(postCreationInput.toReq(userId));
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.CREATE_POST_SUCCESS);
    }

    @Operation(summary = "게시판 - 게시글 전체 조회 API", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<SuccessResponse> getAllPosts() {
        return SuccessResponse.toResponseEntity(SuccessCode.GET_POST_SUCCESS, postUseCase.getAllPosts());
    }

    @Operation(summary = "게시판 - 게시글 단건 조회 API", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @GetMapping("/{postId}")
    public ResponseEntity<SuccessResponse> getPost(@PathVariable Long postId) {
        return SuccessResponse.toResponseEntity(SuccessCode.GET_POST_SUCCESS, postUseCase.viewPost(postId));
    }

    @Operation(summary = "게시판 - 사용자ID로 게시글 조회 API", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @GetMapping
    public ResponseEntity<SuccessResponse> getPostByUserId(@RequestParam("userId") Long userId) {
        return SuccessResponse.toResponseEntity(SuccessCode.GET_USER_POST_LIST_SUCCESS, postUseCase.getPostsByUserId(userId));
    }

    @Operation(summary = "게시판 - 게시글 수정 API", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseWithoutResult.class)))
    })
    @PutMapping("/{postId}")
    public ResponseEntity<?> modifyPost(@PathVariable Long postId,
                                        @RequestParam Long userId,
                                        @RequestParam String title,
                                        @RequestParam String content) {
        PostModifyInput postModifyInput = PostModifyInput.builder()
                .title(title)
                .content(content)
                .build();

        postUseCase.modifyPost(postId, postModifyInput.toReq(userId));
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.MODIFY_POST_SUCCESS);
    }

    @Operation(summary = "게시판 - 게시글 삭제 API", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseWithoutResult.class)))
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@RequestParam Long userId, @PathVariable Long postId) {
        postUseCase.deletePost(postId, userId);
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.DELETE_POST_SUCCESS);
    }

    @Operation(summary = "게시판 - 게시글 통합 검색 API",
            description = "title에만 값을 넣으면 제목 검색, content에만 값을 넣으면 내용 검색, title과 content에 같은 내용을 넣으면 제목+내용 검색 입니다",
            responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<SuccessResponse> searchPosts(@RequestParam(name = "title", required = false) String title,
                                                       @RequestParam(name = "content", required = false) String content) {
        PostSearchInput postSearchInput = PostSearchInput.builder()
                .title(title)
                .content(content)
                .build();

        return SuccessResponse.toResponseEntity(SuccessCode.GET_POST_SUCCESS, postUseCase.searchPosts(postSearchInput));
    }


}
