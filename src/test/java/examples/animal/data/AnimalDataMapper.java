/**
 *    Copyright 2016-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package examples.animal.data;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.mybatis.dynamic.sql.delete.render.DeleteStatement;
import org.mybatis.dynamic.sql.insert.render.InsertSelectStatement;
import org.mybatis.dynamic.sql.insert.render.InsertStatement;
import org.mybatis.dynamic.sql.select.render.SelectStatement;
import org.mybatis.dynamic.sql.update.render.UpdateStatement;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.where.render.WhereClauseAndParameters;

public interface AnimalDataMapper {

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="AnimalDataResult", value={
        @Result(column="id", property="id", id=true),
        @Result(column="animal_name", property="animalName"),
        @Result(column="brain_weight", property="brainWeight"),
        @Result(column="body_weight", property="bodyWeight")
    })
    List<AnimalData> selectMany(SelectStatement selectStatement);
    
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    List<Map<String, Object>> generalSelect(SelectStatement selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    Long selectALong(SelectStatement selectStatement);
    
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    Double selectADouble(SelectStatement selectStatement);
    
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatement deleteStatement);

    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatement updateStatement);
    
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatement<AnimalData> insertStatement);

    @InsertProvider(type=SqlProviderAdapter.class, method="insertSelect")
    int insertSelect(InsertSelectStatement insertSelectStatement);
    
    @Select({
        "select id, animal_name, brain_weight, body_weight",
        "from AnimalData",
        "${whereClause}"
    })
    @ResultMap("AnimalDataResult")
    List<AnimalData> selectByExample(WhereClauseAndParameters whereClause);

    @Select({
        "select a.id, a.animal_name, a.brain_weight, a.body_weight",
        "from AnimalData a",
        "${whereClause}"
    })
    @ResultMap("AnimalDataResult")
    List<AnimalData> selectByExampleWithAlias(WhereClauseAndParameters whereClause);

    @Select({
        "select id, animal_name, brain_weight, body_weight",
        "from AnimalData",
        "${whereSupport.whereClause}",
        "order by id",
        "OFFSET #{offset,jdbcType=INTEGER} LIMIT #{limit,jdbcType=INTEGER}"
    })
    @ResultMap("AnimalDataResult")
    List<AnimalData> selectByExampleWithLimitAndOffset(@Param("whereSupport") WhereClauseAndParameters whereClause,
            @Param("limit") int limit, @Param("offset") int offset);

    @Select({
        "select b.id, b.animal_name, b.brain_weight, b.body_weight",
        "from AnimalData b",
        "${whereSupport.whereClause}",
        "order by id",
        "OFFSET #{offset,jdbcType=INTEGER} LIMIT #{limit,jdbcType=INTEGER}"
    })
    @ResultMap("AnimalDataResult")
    List<AnimalData> selectByExampleWithAliasLimitAndOffset(@Param("whereSupport") WhereClauseAndParameters whereClause,
            @Param("limit") int limit, @Param("offset") int offset);
}
