import json
import string
import random
from enum import Enum

from flask_socketio import SocketIO, join_room, emit

from flask import current_app

from flask import Blueprint, jsonify
from flask_jwt_extended import jwt_required, get_jwt_identity, decode_token

from database.models import User, socketio
from managers.identity_manager import get_user, get_decoded_data

lobby_bp = Blueprint('lobby', __name__)

rooms = {}  # room_code -> { "creator": str, "started": bool, "players": {}, "winner": None }

class RoomStatus(Enum):
    Waiting=1,
    Started=2,
    Finished=3

class PlayerStatus(Enum):
    Host = 1,
    Player = 2

class PlayerInfo:
    def __init__(self, user_data: User, role:PlayerStatus):
        self.role = role
        self.id = user_data.id
        self.username = user_data.username

def get_code(length):
    return ''.join(random.choices(string.ascii_uppercase + string.digits, k=length))

def new_code():
    length = 6
    code = get_code(length)
    while code in rooms.keys():
        code = get_code(length)
    return code

# Create new user lobby
@lobby_bp.route('/lobby', methods=['POST'])
@jwt_required()
def create_room():
    identity = get_jwt_identity()
    data, status = get_user(identity)
    if status != 200:
        return data, status
    room_code = new_code() # When authentication is successful, allow for creation of the room
    rooms[room_code] = {"created_by":identity,
                        "status":RoomStatus.Waiting,
                        "players": [
                            PlayerInfo(data, PlayerStatus.Host)
                        ], }
    return jsonify({"room_code": room_code}), 200

@lobby_bp.route('/lobby/<code>/start', methods=['POST'])
@jwt_required()
def start_room(code):
    identity = get_jwt_identity()
    data, status = get_user(identity)
    if status != 200:
        return data, status
    #Additional verification required, am I the owner of this room?
    rooms[code]["status"] = RoomStatus.Started

@socketio.on('connect')
def connect(auth):
    token = auth.get("token") if auth else None
    room = auth.get("room") if auth else None
    if token is None or room is None:
        return False
    decoded = decode_token(token)
    current_app.logger.info(decoded)
    user = get_decoded_data(json.loads(decoded["sub"])["username"])
    if not user:
        return False
    if room not in rooms.keys() or rooms[room]["status"] != RoomStatus.Waiting:
        return False
    rooms[room]['players'].append(PlayerInfo(user, PlayerStatus.Player))
    join_room(room)
    emit('lobby_join', {"id": user.id, "username": user.username }, to=room)
