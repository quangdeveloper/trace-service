package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface BusinessAccDAO {
  Map findAccounts(Map map) throws SQLException, CloneNotSupportedException;

  Map findAccountById(Map map) throws SQLException, CloneNotSupportedException;

  Map editAccount(Map map) throws SQLException, CloneNotSupportedException;

  Map findCompanies(Map map) throws SQLException, CloneNotSupportedException;

  Map findCompanyById(Map map) throws SQLException, CloneNotSupportedException;

  Map editCompany(Map map) throws SQLException, CloneNotSupportedException;

  Map findEmployees(Map map) throws SQLException, CloneNotSupportedException;

  Map findEmployeeById(Map map) throws SQLException, CloneNotSupportedException;

  Map editEmployee(Map map) throws SQLException, CloneNotSupportedException;

  Map findEmpProfiles(Map map) throws SQLException, CloneNotSupportedException;

  Map findEmpProfileById(Map map) throws SQLException, CloneNotSupportedException;

  Map editEmpProfile(Map map) throws SQLException, CloneNotSupportedException;

  Map downloadEmpProfile(Map map) throws SQLException, CloneNotSupportedException;

  Map removeEmpProfileFile(Map map) throws SQLException, CloneNotSupportedException;

  Map cbbGetProfileTypes() throws SQLException, CloneNotSupportedException;
}
