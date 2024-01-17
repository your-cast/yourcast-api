package com.yourcast.api.hibernate.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

  ADMIN_READ("admin:read"),
  ADMIN_UPDATE("admin:update"),
  ADMIN_CREATE("admin:create"),
  ADMIN_DELETE("admin:delete"),
  CREATOR_READ("creator:read"),
  CREATOR_UPDATE("creator:update"),
  CREATOR_CREATE("creator:create"),
  CREATOR_DELETE("creator:delete");

  @Getter
  private final String permission;
}
