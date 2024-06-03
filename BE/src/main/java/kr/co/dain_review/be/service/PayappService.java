package kr.co.dain_review.be.service;


import kr.co.dain_review.be.model.payapp.*;
import kr.co.dain_review.be.model.payapp.Result.CommonResult;
import kr.co.dain_review.be.model.payapp.Result.PayappAccountResult;
import kr.co.dain_review.be.model.payapp.Result.PayappCashStResult;

import java.io.IOException;

public interface PayappService {
    /**
     * 페이앱 결제 요청
     * @param payappID 				: 페이앱 아이디
     * @param accountData 			: 결제 정보
     * @return
     * @throws IOException
     */
    PayappAccountResult requestAccount (String payappID, PayappAccountData accountData) throws IOException;

    /**
     * 페이앱 결제 요청 취소
     * @param payappID				: 페이앱 아이디
     * @param accountCancelData		: 취소 정보
     * @return
     * @throws IOException
     */
    CommonResult requestAccountCancel (String payappID, PayappAccountCancelData accountCancelData) throws IOException;

    /**
     * 페이앱 결제 취소 요청
     * @param payappID				: 페이앱 아이디
     * @param accountCancelReqData	: 취소요청 정보
     * @return
     * @throws IOException
     */
    CommonResult requestAccountCancel (String payappID, PayappAccountCancelReqData accountCancelReqData) throws IOException;

    /**
     * 페이앱 정기 결제 요청
     * @param payappID
     * @param accountData
     * @return
     */
    PayappAccountResult requestRegularAccount(String payappID, PayappAccountData accountData) throws IOException;

    /**
     * 페이앱 정기 결제 취소 요청
     * @param userid
     * @param data
     * @return
     */
    CommonResult requestAccountRegularCancel(String userid, PayappAccountCancelReqData data) throws  IOException;

    /**
     * 부계정 등록
     * @param payappID				: 페이앱 아이디
     * @param subidRegistData		: 부계정 등록 정보
     * @return
     * @throws IOException
     */
    CommonResult subidRegist (String payappID, PayappSubidRegistData subidRegistData) throws IOException;


    /**
     * 현금영수증 발행
     * @param payappID				: 페이앱 아이디
     * @param cashStData			: 현금영수증 발행 정보
     * @return
     * @throws IOException
     */
    PayappCashStResult PayappCashSt(String payappID, PayappCashStData cashStData) throws IOException;


    /**
     * 현금영수증 발행 취소
     * @param payappID				: 페이앱 아이디
     * @param cashStCnData			: 현금영수증 발행 취소 정보
     * @return
     * @throws IOException
     */
    CommonResult PayappCashStCn(String payappID, PayappCashStCnData cashStCnData) throws IOException;
}
