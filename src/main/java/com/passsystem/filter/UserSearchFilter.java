package com.passsystem.filter;

public record UserSearchFilter(
        Integer pageSize,
        Integer pageNumber
) {
}
