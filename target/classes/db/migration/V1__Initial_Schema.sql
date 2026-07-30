-- Create users table
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL CHECK (role IN ('ADMIN', 'MANAGER', 'ANALYST')),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create uploaded_datasets table
CREATE TABLE uploaded_datasets (
    dataset_id SERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    upload_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    uploaded_by BIGINT NOT NULL REFERENCES users(user_id),
    total_records INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'VALIDATING', 'VALIDATED', 'PREPROCESSING', 'PREPROCESSED', 'FAILED')),
    file_path VARCHAR(500),
    error_message TEXT
);

-- Create customers table
CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    age INTEGER NOT NULL,
    gender VARCHAR(10),
    income DOUBLE PRECISION NOT NULL,
    education VARCHAR(50),
    marital_status VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create customer_behavior table
CREATE TABLE customer_behavior (
    behavior_id SERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customers(customer_id),
    website_visits INTEGER NOT NULL,
    purchase_count INTEGER NOT NULL,
    last_purchase_date TIMESTAMP,
    total_spending DOUBLE PRECISION NOT NULL,
    engagement_score DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create customer_segments table
CREATE TABLE customer_segments (
    segment_id SERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customers(customer_id),
    recency DOUBLE PRECISION NOT NULL,
    frequency DOUBLE PRECISION NOT NULL,
    monetary DOUBLE PRECISION NOT NULL,
    rfm_score VARCHAR(50) NOT NULL,
    segment_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create campaigns table
CREATE TABLE campaigns (
    campaign_id SERIAL PRIMARY KEY,
    campaign_name VARCHAR(255) NOT NULL,
    campaign_type VARCHAR(100) NOT NULL,
    channel VARCHAR(100) NOT NULL,
    cost DOUBLE PRECISION NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'DRAFT' CHECK (status IN ('DRAFT', 'SCHEDULED', 'ACTIVE', 'COMPLETED', 'PAUSED')),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create campaign_responses table
CREATE TABLE campaign_responses (
    response_id SERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customers(customer_id),
    campaign_id BIGINT NOT NULL REFERENCES campaigns(campaign_id),
    opened BOOLEAN NOT NULL,
    clicked BOOLEAN NOT NULL,
    responded BOOLEAN NOT NULL,
    conversion BOOLEAN NOT NULL,
    revenue DOUBLE PRECISION NOT NULL,
    response_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create predictions table
CREATE TABLE predictions (
    prediction_id SERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customers(customer_id),
    model_version VARCHAR(50) NOT NULL,
    probability DOUBLE PRECISION NOT NULL,
    confidence_score DOUBLE PRECISION NOT NULL,
    risk_score DOUBLE PRECISION NOT NULL,
    risk_category VARCHAR(50) NOT NULL,
    recommended_action TEXT NOT NULL,
    prediction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create campaign_outcomes table
CREATE TABLE campaign_outcomes (
    outcome_id SERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customers(customer_id),
    campaign_id BIGINT NOT NULL REFERENCES campaigns(campaign_id),
    prediction_id BIGINT REFERENCES predictions(prediction_id),
    actual_conversion BOOLEAN NOT NULL,
    actual_revenue DOUBLE PRECISION NOT NULL,
    campaign_result VARCHAR(100) NOT NULL,
    outcome_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create model_metrics table
CREATE TABLE model_metrics (
    metric_id SERIAL PRIMARY KEY,
    model_name VARCHAR(100) NOT NULL,
    version VARCHAR(50) NOT NULL,
    accuracy DOUBLE PRECISION NOT NULL,
    precision_score DOUBLE PRECISION NOT NULL,
    recall_score DOUBLE PRECISION NOT NULL,
    f1_score DOUBLE PRECISION NOT NULL,
    auc_score DOUBLE PRECISION NOT NULL,
    evaluation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create retraining_logs table
CREATE TABLE retraining_logs (
    log_id SERIAL PRIMARY KEY,
    old_model_version VARCHAR(50) NOT NULL,
    new_model_version VARCHAR(50) NOT NULL,
    old_accuracy DOUBLE PRECISION NOT NULL,
    new_accuracy DOUBLE PRECISION NOT NULL,
    training_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    remarks TEXT
);

-- Create ai_insights table
CREATE TABLE ai_insights (
    insight_id SERIAL PRIMARY KEY,
    category VARCHAR(100) NOT NULL,
    insight_text TEXT NOT NULL,
    confidence DOUBLE PRECISION NOT NULL,
    generated_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for performance optimization
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_customer_behavior_customer_id ON customer_behavior(customer_id);
CREATE INDEX idx_customer_segments_customer_id ON customer_segments(customer_id);
CREATE INDEX idx_campaign_responses_customer_id ON campaign_responses(customer_id);
CREATE INDEX idx_campaign_responses_campaign_id ON campaign_responses(campaign_id);
CREATE INDEX idx_predictions_customer_id ON predictions(customer_id);
CREATE INDEX idx_campaign_outcomes_customer_id ON campaign_outcomes(customer_id);
CREATE INDEX idx_campaign_outcomes_campaign_id ON campaign_outcomes(campaign_id);
CREATE INDEX idx_model_metrics_model_name ON model_metrics(model_name);
