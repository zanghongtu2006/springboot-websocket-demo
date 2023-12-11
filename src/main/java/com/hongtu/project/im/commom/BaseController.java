package com.hongtu.project.im.commom;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class BaseController {
    public <T, M> PageResponse<T> model2dto(Page<M> model, Class<T> clazz) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setPage(model.getPageable().getPageNumber());
        pageResponse.setPageSize(model.getPageable().getPageSize());
        pageResponse.setTotal(model.getTotalElements());
        pageResponse.setRows(model2dto(model.getContent(), clazz));
        return pageResponse;
    }

    public <T, M> List<T> model2dto(List<M> models, Class<T> clazz) {
        List<T> dtos = new LinkedList<>();
        for (M model : models) {
            T dto = model2dto(model, clazz);
            if (dto != null) {
                dtos.add(dto);
            }
        }
        return dtos;
    }

    public <T, M> T model2dto(M model, Class<T> clazz) {
        try {
            T dto = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(model, dto);
            return dto;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
