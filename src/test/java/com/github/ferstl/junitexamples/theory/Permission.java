package com.github.ferstl.junitexamples.theory;

enum Permission {

  ___(0, "---"),
  __X(1, "--x"),
  _W_(2, "-w-"),
  _WX(3, "-wx"),
  R__(4, "r--"),
  R_X(5, "r-x"),
  RW_(6, "rw-"),
  RWX(7, "rwx");

  final int iPermission;
  final String sPermission;

  private Permission(int iPermission, String sPermission) {
    this.iPermission = iPermission;
    this.sPermission = sPermission;
  }

  @Override
  public String toString() {
    return this.sPermission;
  }
}