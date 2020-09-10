package com.pig4cloud.pig.member.util;

import com.pig4cloud.pig.member.dto.Member;
import com.pig4cloud.pig.member.entity.TbMember;

/**
 * dto对象转换类
 *
 * @author hanguilin
 */
public class DtoUtil {

	/**
	 * tbMember实体转Member
	 *
	 * @param tbMember 用户
	 * @return Member
	 */
	public static Member tbMemberToMember(TbMember tbMember) {
		Member member = new Member();
		member.setId(tbMember.getId());
		member.setUserName(tbMember.getUserName());
		member.setEmail(tbMember.getEmail());
		member.setTelephone(tbMember.getTelephone());
		member.setAddress(tbMember.getAddress());
		member.setBalance(tbMember.getBalance());
		member.setFile(tbMember.getFile());
		member.setPoints(tbMember.getPoints());
		member.setSex(tbMember.getSex());
		member.setDescription(tbMember.getDescription());
		return member;
	}
}
