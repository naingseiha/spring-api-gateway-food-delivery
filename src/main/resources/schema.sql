CREATE SEQUENCE IF NOT EXISTS api_route_id_seq START with 1;

CREATE TABLE IF NOT EXISTS api_route (
    id BIGINT PRIMARY KEY DEFAULT nextval('api_route_id_seq'),
    uri VARCHAR(255) NOT NULL,
    path VARCHAR(255) NOT NULL,
    method VARCHAR(10) NOT NULL,
    description VARCHAR(255),
    group_code VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
)