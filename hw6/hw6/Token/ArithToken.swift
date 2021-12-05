//
//  ArithToken.swift
//  hw6
//
//  Created by Nikolay Yarlychenko on 05.12.2021.
//

import Foundation

public protocol ArithToken {
    func accept(_ visitor: TokenVisitor)
}
