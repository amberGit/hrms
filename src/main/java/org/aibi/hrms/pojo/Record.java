package org.aibi.hrms.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by John on 2015/12/27.
 */
public class Record {
    private int id;
    private BigDecimal amount;
    private int rid;
    private Date createDate;
    private Date updateDate;
    private Integer installment_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getInstallment_id() {
        return installment_id;
    }

    public void setInstallment_id(Integer installment_id) {
        this.installment_id = installment_id;
    }

}
