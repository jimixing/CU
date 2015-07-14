package com.ebao.gs.pol.prdt.config.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ebao.gs.pol.prdt.config.dao.SectionRelationDao;
import com.ebao.pub.util.DBean;

public class SectionRelationDaoImpl implements SectionRelationDao {

	@Override
	public String getTagNameBySectionName(String sectionName, String subSectionName) throws Exception {
		// TODO Auto-generated method stub
		DBean db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			db = new DBean();
			db.connect();
			Connection conn = db.getConnection();
			
			List<Object> sqlParams = new ArrayList<Object>();
			String sql = this.getTagNameBySectionNameQuerySql(sqlParams, sectionName, subSectionName);
			
			ps = conn.prepareStatement(sql);
			DBean.setParameters(ps, sqlParams);
			rs = ps.executeQuery();
			
			String tagName = null;
			while (rs.next()){
				tagName = rs.getString("tag_name");
			}
			return tagName;
		} catch (Exception e) {
			throw e;
		} finally {
			DBean.closeAll(rs, ps, db);
		}
	}
	
	protected String getTagNameBySectionNameQuerySql(List<Object> sqlParams, String sectionName, String subSectionName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("  select tag_name from t_uiconfig_section_relation where 1=1 ");
		
		if (StringUtils.isNotEmpty(sectionName)) {
			sb.append(" and section_name = ?");
			sqlParams.add(sectionName);
		}
		
		if (StringUtils.isNotEmpty(subSectionName)) {
			sb.append(" and sub_section_name = ?");
			sqlParams.add(subSectionName);
		} else {
			sb.append(" and sub_section_name is null");
		}
		
		return sb.toString();
	}

}
