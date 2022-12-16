package com.bucwith.dto.bucket;

import com.bucwith.domain.bucket.Bucket;
import com.bucwith.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class BucketResDto {
    private User user;
    private Bucket bucket;
}
