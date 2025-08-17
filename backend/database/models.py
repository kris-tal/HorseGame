from flask_sqlalchemy import SQLAlchemy
from werkzeug.security import generate_password_hash, check_password_hash
from sqlalchemy.dialects.postgresql import ENUM

db = SQLAlchemy()

class User(db.Model):
    __tablename__ = 'users'
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(80), unique=True, nullable=False)
    email = db.Column(db.String(120), unique=False, nullable=True)
    password_hash = db.Column(db.String(128), nullable=False)
    created_at = db.Column(db.DateTime, server_default=db.func.now())

    def set_password(self, password):
        self.password_hash = generate_password_hash(password)

    def check_password(self, password):
        return check_password_hash(self.password_hash, password)



# Temporarily defined enum for horse rases, possibly this will reference a table with different breeds
horse_races_enum = ENUM('arabian', 'mustang', 'shire', 'mule', name='horse_races', create_type=False)

# Model for a horse
class Horse(db.Model):
    __tablename__ = 'horses'

    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(80), nullable=True)
    race = db.Column(horse_races_enum, nullable=True)
    speed = db.Column(db.Integer, default=10)
    stamina = db.Column(db.Integer, default=10)
    owner_id = db.Column(db.Integer, db.ForeignKey('users.id', ondelete='CASCADE'), nullable=False)

    owner = db.relationship('User', backref=db.backref('horses', cascade='all, delete'))

    def __repr__(self):
        return f'<Horse {self.name} ({self.race})>'