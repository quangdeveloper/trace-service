package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface CompanyDAO {
  Map findInfo(Map map) throws SQLException, CloneNotSupportedException;

  Map submitChangeCompanyInfo(Map map) throws SQLException, CloneNotSupportedException;

  Map editOwnerInfo(Map map) throws SQLException, CloneNotSupportedException;

  Map editCompanyInfo(Map map) throws SQLException, CloneNotSupportedException;

  Map findCompanies(Map map) throws SQLException, CloneNotSupportedException;

  Map findCompanyById(Map map) throws SQLException, CloneNotSupportedException;

  Map editEmpInfo(Map map) throws SQLException, CloneNotSupportedException;

  Map editEmpProfile(Map map) throws SQLException, CloneNotSupportedException;
}
