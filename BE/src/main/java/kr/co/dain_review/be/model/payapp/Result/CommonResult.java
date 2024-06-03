package kr.co.dain_review.be.model.payapp.Result;

/**
 * Created by kimjinwon on 2016. 11. 11..
 */
public class CommonResult {

    /**
     * 성공 실패 여부
     */
    boolean success;


    /**
     * 실패시 오류 메시지
     */
    String errorMessage;


    public CommonResult(boolean success, String errorMessage) {
        super ();
        this.success = success;
        this.errorMessage = errorMessage;
    }


    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }


    @Override
    public String toString() {
        return "CommonResult{" +
                "success=" + success +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
