package by.grsu.schedule.model.analytics;

import java.util.Set;

public interface AnalyticsModule<I, O> {

    /**
     * @return Описание аналитического модуля
     */
    String getDescription();

    /**
     * @return Область применения модуля
     */
    Set<ModuleScope> getScope();

    /**
     * @return Отображаемое название модуля
     */
    default String getDisplayName() {
        return getSystemName();
    }

    /**
     * @return Уникальное имя для модуля
     */
    default String getSystemName() {
        String className = this.getClass().getSimpleName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

    /**
     * Анализирует входные данные и возвращает результат анализа
     *
     * @param input Входные данные для анализа
     * @return Результат анализа
     */
    AnalysisResult<O> analyze(I input);

    /**
     * @return Сохранять ли результат анализа в случае ошибки
     */
    default boolean saveOnFailure() {
        return true;
    }
}
