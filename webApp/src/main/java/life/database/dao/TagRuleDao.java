package life.database.dao;

import org.springframework.data.repository.CrudRepository;
import life.database.model.TagRule;

public interface TagRuleDao extends CrudRepository<TagRule, Long> {
}
