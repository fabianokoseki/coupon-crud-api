package com.example.coupon.exception;

public enum DAOExceptions {
  UNSIGNED_VALUE_OUT_RANGE(new DAOException("Value is out of range"));
  private final DAOException daoException;

  DAOExceptions(DAOException daoException) {
    this.daoException = daoException;
  }

  public DAOException get() {
    return this.daoException;
  }
}
