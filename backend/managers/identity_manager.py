import json

from flask import jsonify

from database.models import User


def get_user(identity):
    user_data = json.loads(identity)
    user = User.query.filter_by(username=user_data["username"]).first()
    if not user:
        return jsonify({"msg": "Invalid session!"}), 401
    return user, 200