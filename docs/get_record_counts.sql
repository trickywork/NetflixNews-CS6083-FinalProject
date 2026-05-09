-- SQL Script to get record counts for all tables
-- Run this in MySQL to get actual data for the report

USE netflix_news;

SELECT 'jqy_user' AS table_name, COUNT(*) AS record_count FROM jqy_user
UNION ALL
SELECT 'jqy_account', COUNT(*) FROM jqy_account
UNION ALL
SELECT 'jqy_country', COUNT(*) FROM jqy_country
UNION ALL
SELECT 'jqy_web_series', COUNT(*) FROM jqy_web_series
UNION ALL
SELECT 'jqy_web_series_country', COUNT(*) FROM jqy_web_series_country
UNION ALL
SELECT 'jqy_web_series_dubbing', COUNT(*) FROM jqy_web_series_dubbing
UNION ALL
SELECT 'jqy_web_series_subtitle', COUNT(*) FROM jqy_web_series_subtitle
UNION ALL
SELECT 'jqy_production_house', COUNT(*) FROM jqy_production_house
UNION ALL
SELECT 'jqy_producer', COUNT(*) FROM jqy_producer
UNION ALL
SELECT 'jqy_producer_production_house', COUNT(*) FROM jqy_producer_production_house
UNION ALL
SELECT 'jqy_contract', COUNT(*) FROM jqy_contract
UNION ALL
SELECT 'jqy_schedule', COUNT(*) FROM jqy_schedule
UNION ALL
SELECT 'jqy_feedback', COUNT(*) FROM jqy_feedback;
