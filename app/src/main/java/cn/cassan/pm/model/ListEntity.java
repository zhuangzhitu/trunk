package cn.cassan.pm.model;

import java.io.Serializable;
import java.util.List;

public interface ListEntity<T extends BaseEntity> extends Serializable {

    List<T> getList();
}
