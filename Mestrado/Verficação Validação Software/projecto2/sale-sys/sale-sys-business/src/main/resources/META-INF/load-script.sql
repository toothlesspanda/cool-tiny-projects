INSERT INTO Discount (id, DTYPE, description, percentage, threshold) VALUES (1, 'NoDiscount', 'No discount', 0, 0);
INSERT INTO Discount (id, DTYPE, description, percentage, threshold) VALUES (2, 'ThresholdPercentageDiscount', 'Percentage (above threshold)', 0.1, 50);
INSERT INTO Discount (id, DTYPE, description, percentage, threshold) VALUES (3, 'EligibleProductsDiscount', 'Percentage of total eligible', 0.1, 0);
INSERT INTO Unit (id, description, abbreviation) VALUES (1, 'Kg', 'Kg');
INSERT INTO Unit (id, description, abbreviation) VALUES (2, 'Units', 'un');
INSERT INTO Product (id, prodCod, description, faceValue, qty, discountEligibility, unit_id) VALUES (1, 123, 'Prod 1', 100, 500, 0, 1);
INSERT INTO Product (id, prodCod, description, faceValue, qty, discountEligibility, unit_id) VALUES (2, 124, 'Prod 2', 35, 1000, 1, 2);
INSERT INTO User (username, firstName, lastName, password, role, version, enabled) VALUES ('admin', 'ad', 'min', 'dccbaf3d1a8cfe2f82f9774adb125ebfc39cc6b', 'ADMIN', '2017-05-14 04:22:13', 1)
INSERT INTO User (username, firstName, lastName, password, role, version, enabled) VALUES ('basic', 'basic', 'user', 'dccbaf3d1a8cfe2f82f9774adb125ebfc39cc6b', 'BASIC', '2017-05-14 04:22:13', 1)
-- pass = vvs000 for both users