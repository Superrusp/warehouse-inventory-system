package com.kpi.springlabs.backend.repository.jdbc.impl;

import com.kpi.springlabs.backend.enums.SqlOperationType;
import com.kpi.springlabs.backend.model.BaseEntity;
import com.kpi.springlabs.backend.repository.jdbc.BaseRepository;
import com.kpi.springlabs.backend.utils.JdbcTemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public abstract class BaseRepositoryImpl<T extends BaseEntity> implements BaseRepository<T> {

    @Autowired
    protected JdbcTemplateUtils jdbcTemplateUtils;

    protected abstract String getTableName();

    protected abstract T getMappedEntity(ResultSet resultSet) throws SQLException;

    protected abstract PreparedStatement getPreparedStatementForSqlOperation(T entity, SqlOperationType sqlOperationType) throws SQLException;

    @Override
    public List<T> getAll() {
        LOG.debug("Getting all elements in {}", this.getClass().getName());
        List<T> entities = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", getTableName());

        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = jdbcTemplateUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                T entity = getMappedEntity(resultSet);
                LOG.debug("Mapped Entity: {}", entity);
                entities.add(entity);
            }
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while querying for all entities in {}", this.getClass().getName());
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(resultSet, statement, connection);
        }

        return entities;
    }

    @Override
    public Optional<T> getById(long id) {
        LOG.debug("Getting element by id: {} in {}", id, this.getClass().getName());
        String query = String.format("SELECT * FROM %s WHERE id = ?", getTableName());

        ResultSet resultSet = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = jdbcTemplateUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                T entity = getMappedEntity(resultSet);
                LOG.debug("Mapped Entity: {}", entity);
                return Optional.of(entity);
            }
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while querying for entity(id = {}) in {} ", id, this.getClass().getName());
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(resultSet, statement, connection);
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> save(T entity) {
        LOG.debug("Inserting element {} in {}", entity, this.getClass().getName());
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            statement = getPreparedStatementForSqlOperation(entity, SqlOperationType.INSERT);
            connection = statement.getConnection();
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while inserting entity: {} in {}", entity, this.getClass().getName());
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(null, statement, connection);
        }
        return Optional.of(entity);
    }

    @Override
    public void update(T entity) {
        LOG.debug("Updating element {} in {} ", entity, this.getClass().getName());
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            statement = getPreparedStatementForSqlOperation(entity, SqlOperationType.UPDATE);
            connection = statement.getConnection();
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while updating entity: {} in {}", entity, this.getClass().getName());
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(null, statement, connection);
        }
    }

    @Override
    public void delete(long id) {
        LOG.debug("Deleting element by id {} in {}", id, this.getClass().getName());
        String query = String.format("DELETE FROM %s WHERE id = ?", getTableName());

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = jdbcTemplateUtils.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while deleting entity in {}: {}", this.getClass().getName(), id);
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(resultSet, statement, connection);
        }
    }

    @Override
    public boolean exists(long id) {
        LOG.debug("Checking if exists element {} in {}", id, this.getClass().getName());
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            String query = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
            connection = jdbcTemplateUtils.getConnection();
            statement = connection.prepareStatement(query);

            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                LOG.debug("Entity(id = {}) exists", id);
                return true;
            }
            connection.commit();
        } catch (SQLException e) {
            LOG.error("Error occurred while searching for entity in {}: {}", this.getClass().getName(), id);
            jdbcTemplateUtils.rollbackTransaction(connection);
        } finally {
            jdbcTemplateUtils.closeResources(resultSet, statement, connection);
        }
        LOG.debug("Entity(id = {}) does not exist", id);
        return false;
    }
}
