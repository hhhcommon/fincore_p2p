package com.zb.p2p.customer.web.controller;

import com.zb.p2p.customer.client.domain.SyncCorpInfoReq;
import com.zb.p2p.customer.common.model.BaseRes;
import com.zb.p2p.customer.service.SyncDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


@Controller
@RequestMapping("/sync")
public class SyncHistoryDataController {
 
	@Autowired
	SyncDataService syncDataService;
 
	@ResponseBody
	@RequestMapping(value = "/syncAccountInfo")
	public BaseRes syncAccountInfo(@RequestParam(value = "lastId" ,required = false) String lastId,@RequestParam(value = "pageSize" ,required = false)  String pageSize) {
		syncDataService.syncAccountInfo(  lastId,  pageSize);
		return new BaseRes<>();
	}
	
	@ResponseBody
	@RequestMapping(value = "/syncCards")
	public BaseRes syncCards(@RequestParam(value = "lastId" ,required = false) String lastId,@RequestParam(value = "pageSize" ,required = false)  String pageSize) {
		syncDataService.syncCards(  lastId,  pageSize);
		return new BaseRes<>();
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateHistoryIsBindCard")
	public BaseRes updateHistoryIsBindCard() {
		syncDataService.updateHistoryIsBindCard();
		return new BaseRes<>();
	}

	@ResponseBody
	@RequestMapping(value = "/syncOrgMemberInfo")
	public BaseRes syncCorpInfo(@RequestBody @Valid SyncCorpInfoReq req) {
		syncDataService.syncCorpMemberInfo(req);
		return new BaseRes<>();
	}
}
